package edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;
import edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models.Event;
import edu.neu.madcourse.zhongxiruihao.pomodonut.dayview.DayViewActivity;
import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.AccelProcessService;
import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.RecordAccelService;
import edu.neu.madcourse.zhongxiruihao.pomodonut.voiceRecognition.SpeechActivity;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 4;
    public static final int TIMERS_PER_PAGE = 6;

    private ViewPager viewPager;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ArrayList<CountdownTimersFragment> fragments = new ArrayList<>();
        Event[] events = getEvents();  // events that are going to show on the main screen
        // TODO: handle scenario when events.length == 0
        for (int i = 0; i < NUM_PAGES; ++i) {
            Event[] eventsCurrentPage;
            if (i < events.length / TIMERS_PER_PAGE) {
                eventsCurrentPage = Arrays.copyOfRange(events,
                        i * TIMERS_PER_PAGE, (i + 1) * TIMERS_PER_PAGE);
            } else if (i == events.length / TIMERS_PER_PAGE) {
                eventsCurrentPage = Arrays.copyOfRange(events,
                        i * TIMERS_PER_PAGE, events.length);
            } else {
                // don't show this page
                break;
            }
            fragments.add(CountdownTimersFragment.newInstance(eventsCurrentPage));
        }
        viewPager.setAdapter(new CountdownTimersPagerAdapter(getSupportFragmentManager(), fragments));

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        startSensorServices();


        /*
        final TimerNotification timerNotification=new TimerNotification((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE));
        Intent intent=new Intent(this,MainActivity.class);
        timerNotification.makeNotification(this,intent,"Test","Another test");

        CountDownTimer timer=new CountDownTimer(1000*15,1000) {
            @Override
            public void onTick(long millisUnitilFinished) {
                try{
                    timerNotification.updateNotification("New Test",""+(int)(millisUnitilFinished/1000));
                }
                catch (Exception e){
                }
            }
            @Override
            public void onFinish() {
                timerNotification.cancelNotification();
            }
        }.start();*/



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
            case R.id.action_speech_recognition:
                startActivity(new Intent(MainActivity.this, SpeechActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void startSensorServices(){
       if (!isMyServiceRunning(RecordAccelService.class)){
            startService(new Intent(getBaseContext(), RecordAccelService.class));
            Toast.makeText(this, "RecordAccelService started", Toast.LENGTH_LONG).show();
        }

        if (!isMyServiceRunning(AccelProcessService.class)){
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

    private Event[] getEvents() {
        // TODO: read database and get real events
        final int COUNT = 16;
        Event[] events = new Event[COUNT];
        for (int i = 0;i < COUNT; ++i) {
            events[i] = new Event("Event" + i, i * 1000);
        }
        return events;
    }

}
