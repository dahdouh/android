package com.example.tp3exercice5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

//developped by Karim Dahdouh
public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    public static Camera cam = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                try {
                    // si le device est secoué,allume le flash
                    if (getPackageManager().hasSystemFeature(
                            PackageManager.FEATURE_CAMERA_FLASH)) {
                        cam = Camera.open();
                        Parameters p = cam.getParameters();
                        p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                        cam.setParameters(p);
                        cam.startPreview();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Exception lancé: flash ne peux pas allumer.",
                            Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(MainActivity.this, "smarphone secoué !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

}
