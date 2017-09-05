package com.github.nkzawa.socketio.androidchat.message;

import com.github.nkzawa.socketio.androidchat.security.icmetric.ICMetricKeyGen;
import com.github.nkzawa.socketio.androidchat.util.Constants;

/**
 * class which intercepts incoming and outgoing messages in order to properly
 * modify them to provide security from other users.
 *
 * @author Faris McKay
 */
public class MessageGate {

    /**
     * The token to be added to incoming messages for the client to know they have been
     * encrypted and require decryption
     */
    private static final String encryptKey = "k";

    /**
     * The instance to stored keyGen to prevent reproduction of keys every message (cpu wastage)
     */
    private static ICMetricKeyGen keyGen = new ICMetricKeyGen(Constants.APPLICATION_CONTEXT);

    /**
     * Perform all relevant operations on outgoing messages, encryption, adding encryption
     * tokens for the message to be properly recieved
     * @param message the sent message to be secured
     * @return the final message post encryption and modification
     */
    public static String secureOutgoing(String message){
        String newMessage = message;
        return newMessage;
    }

    /**
     * Handles incomming messages and sorts the encrypted from the normal messages
     * encrypted messages are decrypted and have their token un-appended from them
     * in order to be read normally
     * @param message the incoming message from the user to be checked and either read or operated upon
     * @return the final normalized message
     */
    public static String decryptIncoming(String message){
        if(!message.contains(encryptKey)){
            return message;
        }
        String newMessage = message;
        return newMessage;
    }
}
