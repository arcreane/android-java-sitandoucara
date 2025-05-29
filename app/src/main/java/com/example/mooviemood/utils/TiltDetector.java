package com.example.mooviemood.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

// Classe utilitaire qui détecte l'inclinaison gauche/droite
public class TiltDetector implements SensorEventListener {

    // Interface de rappel utilisée pour déclencher une action quand le user incline l'appareil
    public interface TiltCallback {
        void onTiltLeft();   
        void onTiltRight();  
    }

    private final SensorManager sensorManager;
    private final Sensor rotationSensor;
    private final TiltCallback callback;

    // Seuil d'angle (en degrés) à partir duquel une inclinaison est considérée significative
    private static final float ANGLE_THRESHOLD = 18.0f;

    private enum TiltState {
        NEUTRAL, TILTED_LEFT, TILTED_RIGHT
    }

    private TiltState currentState = TiltState.NEUTRAL;

    public TiltDetector(Context context, TiltCallback callback) {
        this.callback = callback;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) : null;
    }

    public void start() {
        if (sensorManager != null && rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    // Méthode appelée à chaque changement de capteur
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

        float[] orientationAngles = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        float yDegrees = (float) Math.toDegrees(orientationAngles[2]);

        switch (currentState) {
            case NEUTRAL:
                if (yDegrees >= ANGLE_THRESHOLD) {
                    currentState = TiltState.TILTED_RIGHT;
                    callback.onTiltRight();
                } else if (yDegrees <= -ANGLE_THRESHOLD) {
                    currentState = TiltState.TILTED_LEFT;
                    callback.onTiltLeft();
                }
                break;

            case TILTED_LEFT:
            case TILTED_RIGHT:
            // Repasser à l’état neutre si le user revient à une position centrale
                if (yDegrees > -ANGLE_THRESHOLD && yDegrees < ANGLE_THRESHOLD) {
                    currentState = TiltState.NEUTRAL;
                }
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
