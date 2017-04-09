package edu.neu.madcourse.zhongxiruihao.pomodonut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startService(View view){
        Log.d("abc","Start Service");
        startService(new Intent(getBaseContext(),MyService.class));
    }

    public void stopService(View view){
        stopService(new Intent(getBaseContext(),MyService.class));
    }


}
