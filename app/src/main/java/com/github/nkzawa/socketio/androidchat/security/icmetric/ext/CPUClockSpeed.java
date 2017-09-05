package com.github.nkzawa.socketio.androidchat.security.icmetric.ext;

import android.content.Context;

import com.github.nkzawa.socketio.androidchat.security.icmetric.Feature;
import com.github.nkzawa.socketio.androidchat.security.icmetric.FeatureType;
import java.io.InputStream;
import java.io.IOException;
import android.widget.Toast;
import com.github.nkzawa.socketio.androidchat.util.Constants;

/**
 * A file which generates a private key for from icmetric properties of
 * the os and hardware
 *
 * @author Faris McKay
 */
public class CPUClockSpeed extends Feature{

    public CPUClockSpeed(Context context){
        super(context);
    }

    @Override
    public void extract() {
        ProcessBuilder cmd;
        String result="";

        try{
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024];
            while(in.read(re) != -1){
                result = result + new String(re);
            }
            in.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        String numberOnly= result.replaceAll("[^0-9]", "");
        setValue(Double.parseDouble(numberOnly));
    }

    @Override
    public float scale() {
        return 1/1E37f;
    }

    @Override
    public FeatureType type() {
        return FeatureType.NUMERIC;
    }
}
