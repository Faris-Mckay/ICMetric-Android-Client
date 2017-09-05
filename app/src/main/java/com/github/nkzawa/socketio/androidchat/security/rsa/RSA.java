package com.github.nkzawa.socketio.androidchat.security.rsa;

import java.math.BigInteger;

/**
 * @author Faris McKay
 */

public class RSA {

    private BigInteger mod, pubKey, icPrivKey;

    public RSA(String icmetricKey) {
        BigInteger p = new BigInteger("11382123219558869670203764803506885257498674008055205888242200573488346359338641415099742982003086744260882553035397518685887314313874562826761132549825303");
        BigInteger q = new BigInteger("12222969105684082362402986051765281420232428502880152169950402688865916570011589983408587824872112315526831780509335705315995182687884654516848281247347633");
        mod = p.multiply(q);
        BigInteger totient = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        icPrivKey = new BigInteger(icmetricKey);
        while (totient.gcd(icPrivKey).intValue() > 1) {
            icPrivKey = icPrivKey.add(new BigInteger("2"));
            System.out.println(icPrivKey);
        }
        pubKey = icPrivKey.modInverse(totient);
    }

    /**
     * Encrypt the given plaintext message.
     * */
    public synchronized String encrypt(String message) {
        return (new BigInteger(message.getBytes())).modPow(icPrivKey, mod).toString();
    }


    /**
     * Return the modulus.
     * */
    public synchronized BigInteger getMod() {
        return mod;
    }


    /**
     * Return the public key.
     * */
    public synchronized BigInteger getPubKey() {
        return pubKey;
    }

    
}
