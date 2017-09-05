package com.github.nkzawa.socketio.androidchat.security;

import android.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is used to encrypt information with a Strong 128bit AES encryption cypher
 * the usage is new AESEncrytion(key).encrypt(text);
 * the usage of 256bit AES is deemed illegal for standard use by the most current version of
 * java so this has been used in it's stead
 *
 * @author Faris McKay
 */
public class AESEncryption {

    /**
     * The AES key to use for encryption
     */
    private Key key;

    /**
     * Stores information regarding the type of encryption cipher to use
     */
    private Cipher cipher;

    public AESEncryption(String key){
        if(key.length() != 16)
            throw new IllegalArgumentException("Key must be 128bits");
        this.key = new SecretKeySpec(key.getBytes(), "AES");;
    }

    /**
     * Encrypt the given text with AES and key stored by this instance
     * @param text to encrypt
     * @return encrypted text
     */
    public String encrypt(String text) {
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            byte[] encoded = Base64.encode(encrypted, Base64.DEFAULT);
            return new String(encoded);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
