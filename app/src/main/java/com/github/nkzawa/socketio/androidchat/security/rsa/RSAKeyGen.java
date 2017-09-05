package com.github.nkzawa.socketio.androidchat.security.rsa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * A file which generates public and private keys for rsa encryption
 *
 * @author Faris McKay
 */
public class RSAKeyGen {

    /**
     * String to hold name of the encryption algorithm.
     */
    public static final String ENCRYPTION = "RSA";

    public static void generateKey(){
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ENCRYPTION);
            keyGen.initialize(1024);
            final KeyPair key = keyGen.generateKeyPair();

            File privateKeyFile = new File(RSAUtils.PRIVATE_KEY_FILE);
            File publicKeyFile = new File(RSAUtils.PUBLIC_KEY_FILE);

            // Create files to store public and private key
            if (privateKeyFile.getParentFile() != null) {
                privateKeyFile.getParentFile().mkdirs();
            }
            privateKeyFile.createNewFile();

            if (publicKeyFile.getParentFile() != null) {
                publicKeyFile.getParentFile().mkdirs();
            }
            publicKeyFile.createNewFile();

            // Saving the Public key in a file
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(
                    new FileOutputStream(publicKeyFile));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();

            // Saving the Private key in a file
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                    new FileOutputStream(privateKeyFile));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
