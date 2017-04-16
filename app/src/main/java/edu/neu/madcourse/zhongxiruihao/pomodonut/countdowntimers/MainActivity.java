package edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;
import edu.neu.madcourse.zhongxiruihao.pomodonut.dayview.DayViewActivity;
import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.AccelProcessService;
import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.RecordAccelService;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 4;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startSensorServices();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        CountdownTimersFragment[] fragments = new CountdownTimersFragment[NUM_PAGES];
        for (int i = 0; i < NUM_PAGES; ++i) {
            fragments[i] = new CountdownTimersFragment();
        }
        viewPager.setAdapter(new CountdownTimersPagerAdapter(getSupportFragmentManager(),
                NUM_PAGES, fragments));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_day_view:
                startActivity(new Intent(MainActivity.this, DayViewActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startSensorServices(){
        if (!isMyServiceRunning(RecordAccelService.class)) {
            startService(new Intent(getBaseContext(), RecordAccelService.class));
            Toast.makeText(this, "RecordAccelService started", Toast.LENGTH_LONG).show();
        }

        if (!isMyServiceRunning(AccelProcessService.class)) {
            startService(new Intent(getBaseContext(), AccelProcessService.class));
            Toast.makeText(this, "AccelProcess started", Toast.LENGTH_LONG).show();
        }
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

    public void stopSensorServices(View view){
        stopService(new Intent(getBaseContext(),RecordAccelService.class));
        stopService(new Intent(getBaseContext(),AccelProcessService.class));
    }

}
