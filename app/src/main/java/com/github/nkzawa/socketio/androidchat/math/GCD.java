package com.github.nkzawa.socketio.androidchat.math;

/**
 * A class which computes and returns the greatest common denominator between two
 * given values
 *
 * @author Faris McKay
 */

public class GCD {

    /**
     * Stores the first value
     */
    private int a;

    /**
     * Stores the second value
     */
    private int b;

    public GCD(int a, int b){
        this.a = a;
        this.b = b;
    }

    /**
     * Returns the greatest common denominator between the two values
     * @return the highest common denominator
     */
    public int get() {
        int gcd = 0;
        int r = 0;
        a = Math.abs (a);
        b = Math.abs (b);

        while (true) {
            if (b == 0) {
                gcd = a;
                break;
            }
            else {
                r = a % b;
                a = b;
                b = r;
            }
        }
        return gcd;
    }

    /**
     * Returns true if the two values have no common denominations
     * apart from 1
     * @return true when this is the case, otherwise false
     */
    public boolean coprime(){
        return get() == 1 ? true : false;
    }
}
