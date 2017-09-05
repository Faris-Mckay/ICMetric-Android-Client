package com.github.nkzawa.socketio.androidchat.security.icmetric.ext;

import android.content.Context;

import com.github.nkzawa.socketio.androidchat.security.icmetric.Feature;
import com.github.nkzawa.socketio.androidchat.security.icmetric.FeatureType;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import java.util.List;
import android.content.pm.PackageManager;


/**
 * A file which generates a private key for from icmetric properties of
 * the os and hardware
 *
 * @author Faris McKay
 */
public class ApplicationNames extends Feature{

    public ApplicationNames(Context context){
        super(context);
    }

    @Override
    public void extract() {
        final PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            getResult().add(packageInfo.packageName);
        }
    }

    @Override
    public float scale() {
        return 0.001f;
    }

    @Override
    public FeatureType type() {
        return FeatureType.ALPHABETIC;
    }
}
