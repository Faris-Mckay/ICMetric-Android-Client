package com.github.nkzawa.socketio.androidchat.security.icmetric.ext;

import android.content.Context;

import com.github.nkzawa.socketio.androidchat.security.icmetric.Feature;
import com.github.nkzawa.socketio.androidchat.security.icmetric.FeatureType;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import java.util.List;


/**
 * A file which generates a private key for from icmetric properties of
 * the os and hardware
 *
 * @author Faris McKay
 */
public class ApplicationCount extends Feature {

    public ApplicationCount(Context context){
        super(context);
    }

    @Override
    public void extract() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities( mainIntent, 0);
        setValue(pkgAppsList.size());
    }

    @Override
    public float scale() {
        return 0.0001f;
    }

    @Override
    public FeatureType type() {
        return FeatureType.NUMERIC;
    }
}
