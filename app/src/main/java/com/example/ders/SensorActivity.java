package com.example.ders;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor light;
    private Sensor acceleration;
    int preValueLight;
    long beginTime = 0;
    boolean firstFlag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        sensorManager = (SensorManager) getSystemService(SensorActivity.this.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Log.d("SensorActivity", "accuracy: " + accuracy);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public final void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            int value = (int) event.values[0];
            if (preValueLight <= 20 && value > 20) {
                preValueLight = value;
                ConstraintLayout layout = findViewById(R.id.layout);
                layout.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                TextView textView = findViewById(R.id.textView);
                textView.setTextColor(getResources().getColor(android.R.color.secondary_text_light));
            } else if (preValueLight >= 10 && value < 10) {
                preValueLight = value;
                ConstraintLayout layout = findViewById(R.id.layout);
                layout.setBackgroundColor(getResources().getColor(R.color.background));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionBar)));
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(android.R.color.black));
                TextView textView = findViewById(R.id.textView);
                textView.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
            } else {
                preValueLight = value;
            }
            //Log.d("SensorActivity", "event: " + value);
        }
        else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            if (firstFlag) {
                beginTime = event.timestamp;
                firstFlag = false;
            }
            double value =  Math.sqrt(event.values[0]*event.values[0] + event.values[1]*event.values[1] + event.values[2]*event.values[2]);
            if (value > 0.1) {
                beginTime = event.timestamp;
            }
            Log.d("SensorActivity", String.valueOf(event.timestamp - beginTime));
            if (event.timestamp - beginTime > 5000000000L) {
                Toast.makeText(this, "Spplication closed!", Toast.LENGTH_LONG).show();
                System.exit(0);
            }
            //Log.d("SensorActivity", "event: " + event.timestamp);
        }
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, acceleration, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
