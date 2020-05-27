package com.example.tp3exercice1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //final SensorManager sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);

    //List<Sensor> sensorsList = sensorManager.getSensorList(Sensor.TYPE_ALL);
    //TextView mTextView;

    SensorManager smm;
    List<Sensor> sensor;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        listview = (ListView) findViewById (R.id.listview);
        sensor = smm.getSensorList(Sensor.TYPE_ALL);
        listview.setAdapter(new ArrayAdapter<Sensor>(this, android.R.layout.simple_list_item_1,  sensor));


         /*
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mTextView = (TextView) findViewById(R.id.textView1);
        //list = (ListView) findViewById(R.id.listView1);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);
        for (int i = 1; i < sensors.size(); i++) {
            mTextView.append("\n" + sensors.get(i).getName() + "Vendor" + sensors.get(i).getVendor());
        }
        */

    }

  }
