package com.github.nkzawa.socketio.androidchat.util;

import android.app.Activity;
import android.content.Context;

/**
 * Stores all application level constants for re-use
 *
 * @version 20/1/2017
 * @author Faris McKay
 */
public class Constants {

    /**
     * The location of the hosted chat server
     */
    public static final String CHAT_SERVER_URL = "http://172.20.10.4:3000/";

    /**
     * The location of the hosted Login server
     */
    public static final String LOGIN_SERVER_IP = "172.20.10.4";

    /**
     * The standard key to use for the AES encryption system before sending messages to the LS
     */
    public static final String STOCK_MESSAGE_KEY = "bar12345bar12345";

    /**
     * How long to wait for a response from the LS before throwing an exception
     */
    public static final int RESPONSE_WAIT_TIME = 900;

    /**
     * Message to encrypt with ICMetrics to decrypt and see if the keys used match
     */
    public static final String STOCK_ENCRYPTED_MESSAGE = "DOES THIS MESSAGE MAKE SENSE TO YOU?";

    /**
     * Port used to communicate with the login server
     */
    public static final int LOGIN_SERVER_PORT = 4444;

    /**
     * Storage of the application context
     */
    public static Context APPLICATION_CONTEXT = null;

    /**
     * Storage of the application activity
     */
    public static Activity APPLICATION_ACTIVITY = null;

}
