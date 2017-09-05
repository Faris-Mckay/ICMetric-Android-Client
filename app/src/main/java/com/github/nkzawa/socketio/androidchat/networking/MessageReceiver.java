package com.github.nkzawa.socketio.androidchat.networking;

/**
 *
 * @version 07/02/2017
 * @author Faris McKay
 */

public interface MessageReceiver {

    /**
     * Handle the behavior which should be conformed to when a message is taken
     * in from the (login server) port
     * @param message the incoming data
     */
    public void messageReceived(String message);

}
