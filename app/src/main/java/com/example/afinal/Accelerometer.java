package com.example.afinal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class Accelerometer {
    private  SensorManager sensormanager;
    private  Sensor sensor;
    private  SensorEventListener sensorEventlistener;

    public interface Listener
    {
        void onTranslation(float tx, float ty, float tz);
    }
    private Listener listener;
    public void setListener(Listener l)
    {
        listener = l;
    }

    Accelerometer(Context context)
    {
        sensormanager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensormanager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorEventlistener = new SensorEventListener() {
            @Override
            public  void onSensorChanged(SensorEvent sensorEvent){
                if(listener != null){
                    listener.onTranslation(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }
    public void register()
    {
        sensormanager.registerListener(sensorEventlistener, sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister()
    {
        sensormanager.unregisterListener(sensorEventlistener);
    }


}