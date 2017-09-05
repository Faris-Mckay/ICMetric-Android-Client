package com.github.nkzawa.socketio.androidchat.security.icmetric.ext;

import android.content.Context;
import android.widget.Toast;

import com.github.nkzawa.socketio.androidchat.security.icmetric.Feature;
import com.github.nkzawa.socketio.androidchat.security.icmetric.FeatureType;
import android.content.Context;
import android.database.Cursor;
import	android.provider.ContactsContract;
import com.github.nkzawa.socketio.androidchat.util.Constants;
import android.view.ContextThemeWrapper;

/**
 * A file which generates a private key for from icmetric properties of
 * the os and hardware
 *
 * @author Faris McKay
 */
public class ContactNames extends Feature{

    public ContactNames(Context context){
        super(context);
    }

    @Override
    public void extract() {
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null, null,null);
        while (cursor.moveToNext()) {
            getResult().add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        }
    }

    @Override
    public float scale() {
        return 1f;
    }

    @Override
    public FeatureType type() {
        return FeatureType.ALPHABETIC;
    }
}
