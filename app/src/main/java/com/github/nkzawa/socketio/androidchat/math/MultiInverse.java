package com.github.nkzawa.socketio.androidchat.math;

/**
 * @author Faris McKay
 */

public class MultiInverse {

    public static int getInverse(int base, int mod) throws Exception {
         GCD gcdCalc = new GCD(base,mod);
        if(gcdCalc.get()!=1)  {
            throw new Exception("no inverse found for two given numbers");
        }
        int[] coefficients;
        coefficients = findInverse(base,mod);
        if(base*coefficients[0]%mod == 1) {
            return coefficients[0];
        } else  {
            return coefficients[1];
        }
    }

    /**
     * Finds the coefficients of ax+by=d for a (mod b) where d is the gcd(a,b)
     * @param a is the base of which we want the inverse
     * @param b is the modulo
     * @return the array of coefficients
     */
    public static int[] findInverse(int a, int b)  {
        int x = 0, y = 1, lastx = 1, lasty = 0;
        while(b!=0) {
            int quotient = a/b;

            int temp = a;
            a = b;
            b=temp%b;

            temp = x;
            x=lastx-quotient*x;
            lastx=temp;

            temp = y;
            y=lasty-quotient*y;
            lasty=temp;
        }

        int[] coefficients = {lastx, lasty, a};
        return coefficients;
    }
}
