package edu.neu.madcourse.zhongxiruihao.pomodonut;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startService(View view){
        if (!isMyServiceRunning(RecordAccelService.class)) {
            startService(new Intent(getBaseContext(), RecordAccelService.class));
            Toast.makeText(this, "RecordAccelService started", Toast.LENGTH_LONG).show();
        }

        if (!isMyServiceRunning(AccelProcessService.class)) {
            startService(new Intent(getBaseContext(), AccelProcessService.class));
            Toast.makeText(this, "AccelProcess started", Toast.LENGTH_LONG).show();
        }

    }

    public void stopService(View view){
        stopService(new Intent(getBaseContext(),RecordAccelService.class));
        stopService(new Intent(getBaseContext(),AccelProcessService.class));
    }

    private boolean isMyServiceRunning(Class<?> serviceClass){
        ActivityManager manager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)){
            if (serviceClass.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }
}
