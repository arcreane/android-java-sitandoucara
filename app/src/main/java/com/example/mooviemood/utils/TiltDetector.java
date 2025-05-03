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
    private final Sensor gyroscope;
    private final TiltCallback callback;

    private long lastUpdate = 0;
    private static final float TILT_THRESHOLD = 1.0f; 

    public TiltDetector(Context context, TiltCallback callback) {
        this.callback = callback;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) : null;
    }

    public void start() {
        if (sensorManager != null && gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float yRotation = event.values[1]; 
        long currentTime = System.currentTimeMillis();

        if ((currentTime - lastUpdate) > 800) {
            lastUpdate = currentTime;

            if (yRotation > TILT_THRESHOLD) {
                callback.onTiltLeft();
            } else if (yRotation < -TILT_THRESHOLD) {
                callback.onTiltRight();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
