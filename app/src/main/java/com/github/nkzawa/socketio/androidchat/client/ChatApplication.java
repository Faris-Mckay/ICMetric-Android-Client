package com.github.nkzawa.socketio.androidchat.client;

import android.app.Application;

import com.github.nkzawa.socketio.androidchat.util.Constants;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

/**
 *
 * @version 1.0
 * @author nkzawa
 */
public class ChatApplication extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
