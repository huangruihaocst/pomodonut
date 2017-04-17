package edu.neu.madcourse.zhongxiruihao.pomodonut.sensor;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.orm.SugarContext;

import java.util.List;

import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.models.PermanentDataPoint;
import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.models.TemporaryDataPoint;

/**
 * Created by Ben_Big on 4/15/17.
 */

public class AccelProcessService extends Service {
    public static final int INTERVAL=60000;
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
                            "Select * from TEMPORARY_DATA_POINT where time < ?",
                            "" + currentTime
                    );

                    for (int i=0;i<points.size();i++){
                        TemporaryDataPoint point=points.get(i);
                        point.delete();
                    }

                    List<PermanentDataPoint> listOfPoints= CalAverageAccel.calAverage(points,currentTime);
                    for (PermanentDataPoint point : listOfPoints){
                        point.save();
                    }


                    Toast.makeText(thisService, "" + points.size(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception exception){
                    Log.d("abc",exception.toString());
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
}
