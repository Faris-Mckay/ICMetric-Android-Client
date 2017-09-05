package com.github.nkzawa.socketio.androidchat.security.icmetric.ext;

import android.content.Context;

import com.github.nkzawa.socketio.androidchat.security.icmetric.Feature;
import com.github.nkzawa.socketio.androidchat.security.icmetric.FeatureType;
import java.io.RandomAccessFile;
import java.io.IOException;
import android.widget.Toast;
import com.github.nkzawa.socketio.androidchat.util.Constants;

/**
 * A file which generates a private key for from icmetric properties of
 * the os and hardware
 *
 * @author Faris McKay
 */
public class CPUIdleLoad extends Feature{

    public CPUIdleLoad(Context context){
        super(context);
    }

    @Override
    public void extract() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();

            String[] toks = load.split(" ");

            long idle1 = Long.parseLong(toks[5]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {}

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" ");

            long idle2 = Long.parseLong(toks[5]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            setValue((float)(cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1)));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public float scale() {
        return 100f;
    }

    @Override
    public FeatureType type() {
        return FeatureType.NUMERIC;
    }
}
