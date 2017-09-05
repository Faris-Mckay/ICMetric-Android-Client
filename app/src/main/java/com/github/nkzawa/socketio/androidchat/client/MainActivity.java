package com.github.nkzawa.socketio.androidchat.client;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;

import com.github.nkzawa.socketio.androidchat.R;
import com.github.nkzawa.socketio.androidchat.util.Constants;

import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import 	android.os.Build;

/**
 *
 * @version 2.0
 * @author nkzawa
 * @author Faris McKay
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPermissionToReadUserContacts();
        setContentView(R.layout.activity_main);
        Constants.APPLICATION_ACTIVITY = this;
    }

    // Identifier for the permission request
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    // Called when the user is performing an action which requires the app to read the
    public void getPermissionToReadUserContacts() {
        if (Build.VERSION.SDK_INT > 22) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},READ_CONTACTS_PERMISSIONS_REQUEST);
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},READ_CONTACTS_PERMISSIONS_REQUEST);
            }
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode != READ_CONTACTS_PERMISSIONS_REQUEST) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
