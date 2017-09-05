package com.github.nkzawa.socketio.androidchat.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.androidchat.R;
import com.github.nkzawa.socketio.androidchat.networking.ConnectTask;
import com.github.nkzawa.socketio.androidchat.security.AESEncryption;
import com.github.nkzawa.socketio.androidchat.security.icmetric.ICMetricAuth;
import com.github.nkzawa.socketio.androidchat.security.rsa.RSA;
import com.github.nkzawa.socketio.androidchat.util.Constants;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * A login screen which has multi-factor authentication and icmetric key generation for device and user verification
 * this class handles all of the base features of the defence system, all information which is sent to the login server is
 * encrypted beforehand to protect the data and protocols of both the LS and the Client
 *
 * @version 2.0
 * @author Faris McKay
 * @author nkzawa
 */
public class LoginActivity extends Activity {

    private EditText mUsernameView;

    private String mUsername, mPassword;

    private EditText mPasswordView;

    private Socket mSocket;

    private static ConnectTask task;

    public static boolean LOGGING_IN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mUsernameView = (EditText) findViewById(R.id.username_input);
        mPasswordView = (EditText) findViewById(R.id.password_input);
        mUsernameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mSocket.on("login", onLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off("login", onLogin);
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        LOGGING_IN = true;
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        String username = mUsernameView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            mUsernameView.requestFocus();
            return;
        }
        mUsername = username;
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Please enter a Password!");
            mPasswordView.requestFocus();
            return;
        }
        mPassword = password;
        task = new ConnectTask(username, new AESEncryption(Constants.STOCK_MESSAGE_KEY).encrypt(password));
        task.execute(" ");
        try {
            task.waitForCreditialResponse(username, mSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the continued login service for the client following receiving the response from the loginserver
     * @param response the message sent back by the login server
     * @param username the user who is trying to log in
     * @param socket the socket the client is connecting to
     */
    public static void continueLogin(String response, String username, Socket socket){
        //task = null;
        if (!response.equalsIgnoreCase("verification")){
            if (!response.equalsIgnoreCase("synchronization")) {
                Looper.prepare();
                Toast.makeText(Constants.APPLICATION_CONTEXT, "Wrong password.", Toast.LENGTH_SHORT).show();
                LOGGING_IN = false;
                return;
            }
        }
        Looper.prepare();
        Toast.makeText(Constants.APPLICATION_CONTEXT, "Contacting LS....", Toast.LENGTH_SHORT).show();
        try {
            checkICMetricAuthKey(username, socket, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate the ICMetric key and use it to encrypt the stock message and send to the login server
     * for phase 2 for the verification of the device or if phase 1, send the login server the
     * public key generated and mod for future decryption attempts
     * @param username the user trying to log in
     * @param socket the user is trying to connect to
     * @throws IOException when their has been no response from the server
     */
    private static void checkICMetricAuthKey(String username, Socket socket, String response) throws IOException {
        ICMetricAuth auth = new ICMetricAuth();
        double pcaVector = auth.getPCAVector(auth.getFeatureCovariance());
        int icMetricKey = auth.roundResponse(pcaVector) + 1;
        //String icMetricKey = auth.convertTo128BitString(pcaResponse);
        RSA encryption = new RSA(Integer.toString(icMetricKey));
        if(response.equalsIgnoreCase("synchronization")){
            task.mTcpClient.sendMessage(encryption.getPubKey()+"/4/4/4/"+encryption.getMod());
        } else if (response.equalsIgnoreCase("verification")){
            task.mTcpClient.sendMessage("null"+"/4/4/4/"+encryption.encrypt(Constants.STOCK_ENCRYPTED_MESSAGE));
        }
        task.waitForICMetricResponse(username, socket);
    }

    /**
     * Check if the response from the login server confirms the devices identity and allow the connection
     * or refuse them, this is the final defence before login is allowed
     * @param response is the response of the login server
     * @param username is the user trying to log in
     * @param socket the user is  trying to connect to
     */
    public static void finishLogin(String response, String username, Socket socket){
        LOGGING_IN = false;
        task = null;
        if (!response.equalsIgnoreCase("accepted")) {
            // ICMetric key generated does not match stored details
            return;
        }
        socket.emit("add user", username);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            int numUsers;
            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("username", mUsername);
            intent.putExtra("numUsers", numUsers);
            setResult(RESULT_OK, intent);
            finish();
        }
    };
}



