package edu.neu.madcourse.zhongxiruihao.pomodonut;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.orm.SugarContext;

import java.util.List;
import java.util.Timer;

import edu.neu.madcourse.zhongxiruihao.pomodonut.DataStructure.TemporaryDataPoint;

/**
 * Created by Ben_Big on 4/13/17.
 */

public class AccelProcessService extends Service {
    CountDownTimer timer;
    private final Service thisService=this;


    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        SugarContext.init(this);

        timer=new CountDownTimer(30000,3000){
            @Override
            public void onTick(long millisUntilFinished){
                try {
                    long currentTime = System.currentTimeMillis();
                    List<TemporaryDataPoint> points = TemporaryDataPoint.findWithQuery(
                            TemporaryDataPoint.class,
                            "Select * from TEMPORARY_DATA_POINT where time < ?",
                            "" + currentTime
                    );
                    for (int i=0;i<points.size()/3;i++){
                        TemporaryDataPoint point=points.get(i);
                        point.delete();
                    }


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
        Toast.makeText(this,"Service Destroyed",Toast.LENGTH_LONG).show();
    }
}



