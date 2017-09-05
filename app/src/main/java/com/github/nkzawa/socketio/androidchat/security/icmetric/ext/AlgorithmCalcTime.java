package com.github.nkzawa.socketio.androidchat.security.icmetric.ext;

import android.content.Context;

import com.github.nkzawa.socketio.androidchat.security.icmetric.Feature;
import com.github.nkzawa.socketio.androidchat.security.icmetric.FeatureType;
import java.math.BigInteger;

/**
 * A file which generates a private key for from icmetric properties of
 * the os and hardware
 *
 * @author Faris McKay
 */
public class AlgorithmCalcTime extends Feature {

    public AlgorithmCalcTime(Context context){
        super(context);
    }

    @Override
    public void extract() {
        BigInteger factValue = BigInteger.ONE;
        long t1 = System.nanoTime();
        for ( int i = 2; i <= 10000; i++){
            factValue = factValue.multiply(BigInteger.valueOf(i));
        }
        long t2=System.nanoTime();
        setValue(((double)(t2-t1)/1000000));
    }

    @Override
    public float scale() {
        return 0.01f;
    }

    @Override
    public FeatureType type() {
        return FeatureType.NUMERIC;
    }

}
