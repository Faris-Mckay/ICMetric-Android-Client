package com.github.nkzawa.socketio.androidchat.math;

/**
 * This class calculates math operations to find prime numbers which allow us to get suitable
 * values for the RSA ICmetric implementation
 *
 *
 * @author Faris McKay
 */
public class Prime  {

    /**
     * Return true if the parsed in value is a prime number
     * @param value to check
     * @return true if is prime or false if not
     */
    public static boolean isPrime(int value){
        int divisibleCount = 0;
        for(int i = 1;i <= value;i++) {
            if(value % i == 0) {
                divisibleCount++;
            }
        }
        return divisibleCount == 2 ? true : false;
    }

    /**
     * Get the nearest prime number to the parse in value (above)
     * @param num to find the closest prime
     * @return the next prime number
     */
    public static int nearestPrime(int num) {
        int prime = 0;
        for(int i=num;;i++) {
            if(isPrime(i)) {
                prime = i;
                break;
            }
        }
        return prime;
    }
}
