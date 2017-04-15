package edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 4;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        CountdownTimersFragment[] fragments = new CountdownTimersFragment[NUM_PAGES];
        for (int i = 0; i < NUM_PAGES; ++i) {
            fragments[i] = new CountdownTimersFragment();
        }
        viewPager.setAdapter(new CountdownTimersPagerAdapter(getSupportFragmentManager(),
                NUM_PAGES, fragments));
    }

}
