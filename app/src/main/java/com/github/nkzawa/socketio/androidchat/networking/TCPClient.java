package com.github.nkzawa.socketio.androidchat.networking;

import android.util.Log;

import com.github.nkzawa.socketio.androidchat.client.LoginActivity;
import com.github.nkzawa.socketio.androidchat.util.Constants;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * Handles the TCP message communication between this android application and the login server
 * to transfer encrypted data to verify the user and thier ICMetric information
 *
 * @version 07/02/2017
 * @author Faris McKay
 */
public class TCPClient  {

    private MessageReceiver mMessageListener = null;

    private String incomingMessage,username, password;
    private boolean isListening = false;

    private PrintWriter out;
    private BufferedReader in;


    public TCPClient(String username, String password, MessageReceiver listener) {
        mMessageListener = listener;
        this.username = username;
        this.password = password;
    }

    /**
     * Sends the message entered by client to the server
     * @param message text entered by client
     */
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }

    /**
     * Breaks the listening loop
     */
    public void stopClient() {
        isListening = false;
    }

    public void run() {
        isListening = true;
        try {
            InetAddress serverAddr = InetAddress.getByName(Constants.LOGIN_SERVER_IP);
            Socket socket = new Socket(serverAddr, Constants.LOGIN_SERVER_PORT);
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                sendMessage(username +"/4/4/4/"+password);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (isListening && LoginActivity.LOGGING_IN) {
                    incomingMessage = in.readLine();
                    if (incomingMessage != null && mMessageListener != null) {
                        mMessageListener.messageReceived(incomingMessage);
                    }
                    incomingMessage = null;
                }
            } catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            } finally {
                socket.close();
            }
        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }
    }
}