package com.example.tp3exercice3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorMgr;
    TextView sensorinfo;
    Sensor accelerometer;
    View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        view = findViewById(R.id.layout);

        sensorinfo = (TextView) findViewById(R.id.sensorInfoText);
        if (accelerometer != null){
            Toast.makeText(this, "Capteur accélérateur trouvé"+ accelerometer, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Capteur TYPE_PROXIMITY non trouvé", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Récupérer les valeurs du capteur
        float x, y, z;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            if(event.values[0] < 0.2) {
                view.setBackgroundColor(Color.BLUE);
            } else if(event.values[1]>1 && event.values[1] < 3) {
                view.setBackgroundColor(Color.GREEN);
            } else {
                view.setBackgroundColor(Color.RED);
            }
            sensorinfo.setText("value 0 ="+ event.values[0] +", value 1 =" +event.values[1] +", value 2 ="+event.values[2]);
            Toast.makeText(this, " valeur de capteur est changé", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
