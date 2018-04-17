package com.example.kevin.firstapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AccelActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    TextView x, y, z, directionView;

    StringBuilder builder = new StringBuilder();
    float [] history = new float[2];
    String [] direction = {"NONE","NONE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        x = (TextView) findViewById(R.id.accelX);
        y = (TextView) findViewById(R.id.accelY);
        z = (TextView) findViewById(R.id.accelZ);
        directionView = (TextView) findViewById(R.id.direction);


    }

    protected void onResume() {
        super.onResume();
        //replace 1000000 with SensorManager.SENSOR_DELAY_NORMAL for less accuracy and more energy efficiency
        mSensorManager.registerListener(this, mAccelerometer, 1000000);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        //x,y,z
        x.setText("X: " + Math.round(event.values[0]));
        y.setText("Y: " + Math.round(event.values[1]));
        z.setText("Z: " + Math.round(event.values[2]));

        //tilt direction text (registered if delta movement is above 2)
        float xChange = history[0] - event.values[0];
        float yChange = history[1] - event.values[1];

        history[0] = event.values[0];
        history[1] = event.values[1];

        if (xChange < -2){
            direction[0] = "LEFT";
        } else if (xChange > 2){
            direction[0] = "RIGHT";
        } /*else if (xChange >= -2 && xChange <= 2) {
            direction[0] = "NONE";
        }*/

        if (yChange < -2){
            direction[1] = "DOWN";
        } else if (yChange > 2){
            direction[1] = "UP";
        } /*else if (yChange >= -2 && yChange <= 2) {
            direction[1] = "NONE";
        }*/

        builder.setLength(0);
        builder.append("x: ");
        builder.append(direction[0]);
        builder.append(" y: ");
        builder.append(direction[1]);

        directionView.setText(builder.toString());

    }
}
