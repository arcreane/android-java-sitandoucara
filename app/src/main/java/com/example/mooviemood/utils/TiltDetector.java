package com.example.mooviemood.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class TiltDetector implements SensorEventListener {

    public interface TiltCallback {
        void onTiltLeft();
        void onTiltRight();
    }

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final TiltCallback callback;

    private long lastUpdate = 0;
    private static final int TILT_THRESHOLD = 5; // ajustable

    public TiltDetector(Context context, TiltCallback callback) {
        this.callback = callback;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) : null;
    }

    public void start() {
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        long currentTime = System.currentTimeMillis();

        if ((currentTime - lastUpdate) > 800) {
            lastUpdate = currentTime;
            if (x > TILT_THRESHOLD) {
                callback.onTiltLeft();
            } else if (x < -TILT_THRESHOLD) {
                callback.onTiltRight();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
