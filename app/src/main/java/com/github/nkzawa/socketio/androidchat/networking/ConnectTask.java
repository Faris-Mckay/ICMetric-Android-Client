package com.github.nkzawa.socketio.androidchat.networking;

import android.os.AsyncTask;
import com.github.nkzawa.socketio.androidchat.util.Constants;
import com.github.nkzawa.socketio.androidchat.client.LoginActivity;

import java.io.IOException;
import io.socket.client.Socket;

/**
 * Connect the client to the login server and set-up the relevant methods for messages to be sent
 * and received between the two
 *
 * @author Faris McKay
 * @version 9/02/2017
 */
public class ConnectTask extends AsyncTask<String,String,TCPClient> {

    /**
     * The messages that are sent and the messages which have been received
     */
    private String prefix, suffix, containedMessage;

    /**
     * The TCPClient which communicates with the login server
     */
    public TCPClient mTcpClient;

    /**
     * Flags when a new message has arrived
     */
    public boolean newMessage = false;

    /**
     * Use this construct to intialize an authentication request to the login server
     * @param username to check
     * @param password to check
     */
    public ConnectTask(String username, String password){
        this.prefix = username;
        this.suffix = password;
    }

    /**
     * Use this constructor to initialize an ICMetric message to the login server
     * @param message to send to the server
     */
    public ConnectTask(String message){
        this.prefix = "//icm://";
        this.suffix = message;
    }

    /**
     * Set up the connection with the server and listen for incoming
     * messages which are handled by the overriden message received function
     * @param message the incoming message from the port
     * @return null (unneeded)
     */
    @Override
    protected TCPClient doInBackground(String... message) {
        mTcpClient = new TCPClient(prefix, suffix, new MessageReceiver() {
            @Override
            public void messageReceived(String message) {
                containedMessage = message;
                newMessage = true;
            }
        });
        mTcpClient.run();
        return null;
    }

    /**
     * A method which creates a new thread dedicated to giving an reasonable amount of time for the login server
     * to have responded and setting contained message, this prevents errors from occuring when the login activity
     * attempts to read the response instantly before they have arrived
     * @param username the user trying to login
     * @param socket the socket the user is connecting to
     * @throws IOException when there is no response
     */
    public void waitForCreditialResponse(final String username, final Socket socket) throws IOException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Constants.RESPONSE_WAIT_TIME);
                } catch(InterruptedException e){

                }
                newMessage = false;
                if(containedMessage == null){
                    try {
                        throw new Exception("No Response from Login Server!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                LoginActivity.continueLogin(containedMessage, username, socket);
                containedMessage = null;
            }
        }).start();
    }

    /**
     * A method which creates a new thread dedicated to giving an reasonable amount of time for the login server
     * to have responded and setting contained message, this prevents errors from occuring when the login activity
     * attempts to read the response instantly before they have arrived
     * @param username the user trying to login
     * @param socket the socket the user is connecting to
     * @throws IOException when there is no response
     */
    public void waitForICMetricResponse(final String username, final Socket socket) throws IOException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Constants.RESPONSE_WAIT_TIME);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                newMessage = false;
                if(containedMessage == null){
                    try {
                        throw new Exception("No Response from Login Server!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                LoginActivity.finishLogin(containedMessage, username, socket);
                containedMessage = null;
            }
        }).start();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
