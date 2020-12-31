package com.example.demosensor;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import com.example.demosensor.TheService;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.tv);

        /*
        SensorManager sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor proxitySensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        SensorEventListener sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                if (event.values[0] < proxitySensor.getMaximumRange())
                {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    textView.setText("Gần");

                }
                else
                {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    textView.setText("Xa");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(sensorEventListener,proxitySensor, SensorManager.SENSOR_DELAY_NORMAL); // 2*1000*1000
        // */
        textView.setText("Wakeup vinhjaxt!!!");
        Intent intent = new Intent(this, TheService.class);
        startService(intent);

    }
}
