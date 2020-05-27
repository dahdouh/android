package com.example.tp3exercice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView sensorinfo = (TextView) findViewById(R.id.sensorInofText);

        sensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            sensorinfo.setText("Capteur de proximité (TYPE_PROXIMITY) existe sur cet appareil");
            Toast.makeText(this, "Capteur TYPE_PROXIMITY trouvé", Toast.LENGTH_LONG).show();
        }
        else {
            sensorinfo.setText("Capteur de proximité n'existe pas sur cet appareil");
            Toast.makeText(this, "Capteur TYPE_PROXIMITY non trouvé", Toast.LENGTH_SHORT).show();
        }

    }
}
