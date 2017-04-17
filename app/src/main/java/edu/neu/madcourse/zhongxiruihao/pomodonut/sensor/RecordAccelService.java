package edu.neu.madcourse.zhongxiruihao.pomodonut.sensor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.orm.SugarContext;

import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.models.TemporaryDataPoint;

/**
 * Created by Ben_Big on 4/15/17.
 */

public class RecordAccelService extends Service {

    private SensorManager mSensorManager;
    private final Service thisService=this;
    private float mAccelCurrent;
    private float mAccelLast;
    private float mAccel; // last acceleration including gravity
    private SharedPreferences preferences;
    public static final String SERVICE_STATE="recordProcessServiceState";


    @Nullable
    @Override
    public IBinder onBind(Intent intent){

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean(SERVICE_STATE,true).commit();


        mSensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccelCurrent=SensorManager.GRAVITY_EARTH;
        mAccelLast=SensorManager.GRAVITY_EARTH;
        mAccel = 0.00f;

        SugarContext.init(this);


        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mSensorManager.unregisterListener(mSensorListener);
        preferences.edit().putBoolean(SERVICE_STATE,false).commit();
        Toast.makeText(this,"Service Destroyed", Toast.LENGTH_LONG).show();
    }



    private final SensorEventListener mSensorListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x=sensorEvent.values[0];
            float y=sensorEvent.values[1];
            float z=sensorEvent.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            /*
            if (mAccel > 7) {
                Toast.makeText(thisService, "" + mAccel, Toast.LENGTH_SHORT).show();
            }*/
            if (Math.abs(mAccel)>3) {
                long currentTime = System.currentTimeMillis();
                TemporaryDataPoint point = new TemporaryDataPoint(currentTime, Math.abs(mAccel));
                try {
                    point.save();
                }
                catch (Exception e){}
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
