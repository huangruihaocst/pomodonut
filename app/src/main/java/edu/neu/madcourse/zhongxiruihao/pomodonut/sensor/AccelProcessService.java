package edu.neu.madcourse.zhongxiruihao.pomodonut.sensor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.models.PermanentDataPoint;
import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.models.TemporaryDataPoint;

/**
 * Created by Ben_Big on 4/15/17.
 */

public class AccelProcessService extends Service {
    public static final int INTERVAL=3000;
    CountDownTimer timer;
    private final Service thisService=this;
    private SharedPreferences preferences;
    public static final String SERVICE_STATE="accelProcessServiceState";

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        preferences=PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean(SERVICE_STATE,true).commit();

        SugarContext.init(this);

        timer=new CountDownTimer(INTERVAL*10,INTERVAL){
            @Override
            public void onTick(long millisUntilFinished){
                try {
                    long currentTime = System.currentTimeMillis();
                    List<TemporaryDataPoint> points = TemporaryDataPoint.findWithQuery(
                            TemporaryDataPoint.class,
                            "Select * from TEMPORARY_DATA_POINT where time < ? order by time",
                            "" + currentTime
                    );


                    calAverage(points,currentTime);

                    /*
                    for (PermanentDataPoint point : listOfPoints){
                        try {
                            point.save();
                        }
                        catch (Exception e){}
                    }*/


                    Toast.makeText(thisService, "" + points.size(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception exception){
                    Toast.makeText(thisService, "No such table",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFinish(){
                this.start();
            }
        }.start();

        return START_STICKY;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        timer.cancel();
        preferences.edit().putBoolean(SERVICE_STATE,false).commit();
        Toast.makeText(this,"Service Destroyed",Toast.LENGTH_LONG).show();
    }






    public void calAverage(List<TemporaryDataPoint> points, long currentTime){

        if (points.size()==0){
            PermanentDataPoint newPoint =new PermanentDataPoint(currentTime-AccelProcessService.INTERVAL,Math.random()*0.5);
            newPoint.save();
            return;
        }


        int currentStartingPoint=0;
        int currentSize=1;
        double cumulativeSum=points.get(currentStartingPoint).accelerationData;

        for (int i=0;i<points.size();i++){

            TemporaryDataPoint point=points.get(i);
            point.delete();

            if (i==currentStartingPoint) continue;
            if ((points.get(i).time-points.get(currentStartingPoint).time)>AccelProcessService.INTERVAL){
                //Store the result so far
                PermanentDataPoint newPoint=new PermanentDataPoint(
                        points.get(currentStartingPoint).time, cumulativeSum/currentSize
                );
                newPoint.save();

                //Update the current starting point
                currentStartingPoint=i;
                currentSize=1;
                cumulativeSum=points.get(currentStartingPoint).accelerationData;
            }
            else{
                //Added to the current cumulative points
                currentSize++;
                cumulativeSum+=points.get(i).accelerationData;
            }

            if (i==points.size()-1){
                //Store the result so far
                PermanentDataPoint newPoint=new PermanentDataPoint(
                        points.get(currentStartingPoint).time, cumulativeSum/currentSize
                );
                newPoint.save();
            }
        }
    }




}
