package com.github.nkzawa.socketio.androidchat.security.icmetric.ext;

import android.content.Context;

import com.github.nkzawa.socketio.androidchat.security.icmetric.Feature;
import com.github.nkzawa.socketio.androidchat.security.icmetric.FeatureType;

import java.io.File;

/**
 * A file which generates a private key for from icmetric properties of
 * the os and hardware
 *
 * @author Faris McKay
 */
public class DiskFreeSpace extends Feature {

    public DiskFreeSpace(Context context){
        super(context);
    }

    /**
     * Get a list of all filesystem roots on this system
     * add total usable free space to the total free space value
     */
    @Override
    public void extract() {
        File[] roots = File.listRoots();
        for (File root : roots) {
            setValue(value + root.getUsableSpace());
        }
    }

    @Override
    public float scale() {
        return 1f;
    }

    @Override
    public FeatureType type() {
        return FeatureType.NUMERIC;
    }

}
