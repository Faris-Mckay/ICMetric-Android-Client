package com.github.nkzawa.socketio.androidchat.util;

import android.util.Log;
import android.content.Context;
import java.io.OutputStreamWriter;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import android.widget.Toast;
import android.os.Environment;

/**
 * File used for reading and writing between the android application and the storage on
 * the device
 *
 * @author Faris McKay
 * @version 1.0
 */

public class FileUtils {

    /**
     * Write inputted String data to the storage file for username & pass
     * @param data the string to store on the device's memory
     * @param context of the application
     */
    public static void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("test.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Store string to SD card with permissions
     * @param context
     * @param sFileName
     * @param sBody
     */
    public static void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads and returns the string from the memory of the device into the application
     * @param context of the application
     * @return the extracted string from .txt file
     */
    public static String readFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("test.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

}
