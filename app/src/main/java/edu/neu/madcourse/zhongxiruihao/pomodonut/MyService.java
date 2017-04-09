package edu.neu.madcourse.zhongxiruihao.pomodonut;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Ben_Big on 4/9/17.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        final Service s=this;
        CountDownTimer countDownTimer=new CountDownTimer(15000,3000){
            @Override
            public void onTick(long millisUntilFinished){
                Toast.makeText(s,"hello",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinish(){}
        };
        countDownTimer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this,"Service Destroyed",Toast.LENGTH_LONG).show();
    }
}
