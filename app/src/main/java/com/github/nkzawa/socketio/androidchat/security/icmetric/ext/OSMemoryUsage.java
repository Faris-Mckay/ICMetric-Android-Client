package com.github.nkzawa.socketio.androidchat.security.icmetric.ext;

import android.content.Context;

import com.github.nkzawa.socketio.androidchat.security.icmetric.Feature;
import com.github.nkzawa.socketio.androidchat.security.icmetric.FeatureType;

/**
 * A file which generates a private key for from icmetric properties of
 * the os and hardware
 *
 * @author Faris McKay
 */
public class OSMemoryUsage extends Feature {

    public OSMemoryUsage(Context context){
        super(context);
    }

    @Override
    public void extract() {
        long currentMem = Runtime.getRuntime().freeMemory();
        long maxMem = Runtime.getRuntime().maxMemory();

        double memUsage = (double)maxMem - currentMem;
        setValue(memUsage);
    }

    @Override
    public float scale() {
        return 1/1E7f;
    }

    @Override
    public FeatureType type() {
        return FeatureType.NUMERIC;
    }
}
