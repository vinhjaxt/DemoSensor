package com.example.demosensor;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.os.Build;
import android.annotation.TargetApi;
import android.util.Log;
import com.example.demosensor.MainActivity;

public class TheService extends Service implements SensorEventListener {
    private static String a = TheService.class.getName();
    private SensorManager b = null;
    private String channelId = "vinhjaxt_wakeup";
    public PowerManager.WakeLock c = null;
    private Sensor n;
    private PowerManager o;
    private float lastValue = 0;

    public void onAccuracyChanged(Sensor sensor, int i2) {
        // Log.i(a, "onAccuracyChanged()");
    }

    public void onSensorChanged(SensorEvent event) {
        // Log.i(a, "Sensor:" + String.valueOf(event.values[0]));
        float lastValue = this.lastValue;
        this.lastValue = event.values[0];
        boolean isScreenOn = this.o.isScreenOn();
        if (!isScreenOn && lastValue == 0 && event.values[0] > 0 ) {
            this.c.acquire();
            this.c.release();
            return;
        }
    }

    public void onCreate() {
        super.onCreate();
        this.o = (PowerManager) getSystemService(POWER_SERVICE);
        this.c = this.o.newWakeLock((PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), a);
        this.b = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.n = this.b.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // If earlier version channel ID is not used
        // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel  = new NotificationChannel(
                    this.channelId,
                    "vinhjaxt channel",
                    NotificationManager.IMPORTANCE_HIGH
                    );
            channel.setDescription("wakeup");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        this.b.registerListener(this, this.n, SensorManager.SENSOR_DELAY_NORMAL/* 2*1000*1000 */);
    }

    public void onDestroy() {
        this.b.unregisterListener(this);
        stopForeground(true);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private Notification getNotification(){
        Notification.Builder builder = new Notification.Builder(this, this.channelId)
        .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0));
        return builder.build();
    }

    public int onStartCommand(Intent intent, int i2, int i3) {
        super.onStartCommand(intent, i2, i3);
        startForeground(Process.myPid(), getNotification());
        this.b.registerListener(this, this.n, SensorManager.SENSOR_DELAY_NORMAL/* 2*1000*1000 */);
        return START_STICKY;
    }
}