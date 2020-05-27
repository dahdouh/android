package com.example.tp3exercice4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//developped by Karim Dahdouh
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorMgr;
    TextView sensorinfo;
    Sensor accelerometer;
    View view;

    StringBuilder builder = new StringBuilder();

    float [] history = new float[2];
    String [] direction = {"NONE","NONE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        view = findViewById(R.id.layout);

        sensorinfo = (TextView) findViewById(R.id.sensorInfoText);


    }

    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float xChange = history[0] - event.values[0];
        float yChange = history[1] - event.values[1];

        history[0] = event.values[0];
        history[1] = event.values[1];

        if (xChange > 2){
            direction[0] = "gauche";
        }
        else {
            direction[0] = "droite";
        }

        if (yChange > 2){
            direction[1] = "bas";
        }
        else {
            direction[1] = "haut";
        }

        builder.setLength(0);
        builder.append("x: ");
        builder.append(direction[0]);
        builder.append(" y: ");
        builder.append(direction[1]);

        sensorinfo.setText(builder.toString());

        Toast.makeText(this, " ***" + direction[0] + " $$$ "+ direction[1], Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}