package com.github.nkzawa.socketio.androidchat.security.icmetric.ext;

import android.content.Context;

import com.github.nkzawa.socketio.androidchat.security.icmetric.Feature;
import com.github.nkzawa.socketio.androidchat.security.icmetric.FeatureType;
import com.github.nkzawa.socketio.androidchat.util.Constants;

import android.content.Context;
import android.provider.ContactsContract;
import android.database.Cursor;
import android.widget.Toast;

/**
 * A file which generates a private key for from icmetric properties of
 * the os and hardware
 *
 * @author Faris McKay
 */
public class ContactCount extends Feature{

    public ContactCount(Context context){
        super(context);
    }

    @Override
    public void extract() {
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null, null,null);
        while (cursor.moveToNext()) {
            setValue(value + 1);
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
