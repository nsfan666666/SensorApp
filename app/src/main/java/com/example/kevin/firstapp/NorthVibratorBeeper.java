package com.example.kevin.firstapp;

import android.media.ToneGenerator;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class NorthVibratorBeeper extends Thread {

    private NorthMonitor mon;
    private Vibrator v;
    private ToneGenerator t;

    public NorthVibratorBeeper(NorthMonitor mon, Vibrator v, ToneGenerator t){
        this.mon = mon;
        this.v = v;
        this.t = t;
    }

    @Override
    public void run() {
        while(true) {
            mon.waitForNorth();
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200,VibrationEffect.DEFAULT_AMPLITUDE));
            }else{
                //deprecated in API 26
                v.vibrate(200);
            }

            t.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
