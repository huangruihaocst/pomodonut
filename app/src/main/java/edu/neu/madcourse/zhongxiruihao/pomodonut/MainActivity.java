package edu.neu.madcourse.zhongxiruihao.pomodonut;

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

        Toast.makeText(this,"Service started",Toast.LENGTH_LONG).show();

        startService(new Intent(getBaseContext(),RecordAccelService.class));

        startService(new Intent(getBaseContext(),AccelProcessService.class));
    }

    public void stopService(View view){
        stopService(new Intent(getBaseContext(),RecordAccelService.class));
    }


}
