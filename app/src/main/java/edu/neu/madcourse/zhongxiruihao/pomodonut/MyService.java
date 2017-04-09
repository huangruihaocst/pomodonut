package edu.neu.madcourse.zhongxiruihao.pomodonut;

import android.app.Service;
import android.content.Intent;
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
        Toast.makeText(this,"Service Started",Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this,"Service Destroyed",Toast.LENGTH_LONG).show();
    }
}
