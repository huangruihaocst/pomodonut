package edu.neu.madcourse.zhongxiruihao.pomodonut.donut;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;
import edu.neu.madcourse.zhongxiruihao.pomodonut.dayview.DayViewActivity;
import edu.neu.madcourse.zhongxiruihao.pomodonut.voiceRecognition.SpeechActivity;

/**
 * Created by Ben_Big on 4/23/17.
 */

public class DonutActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private final static int NUM_OF_VIEWS=100;
    protected  enum viewType{
        DAY,MONTH,YEAR
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);
        Toolbar toolbar = (Toolbar) findViewById(R.id.donut_toolbar);
        setSupportActionBar(toolbar);


        mViewPager=(ViewPager) findViewById(R.id.donut_view_pager);
        FragmentManager fragmentManager=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                DonutFragment donut=new DonutFragment();
                donut.init(NUM_OF_VIEWS-1-position,viewType.DAY) ;
                return donut;
            }

            @Override
            public int getCount() {
                return NUM_OF_VIEWS;
            }

        });

        mViewPager.setCurrentItem(NUM_OF_VIEWS-1);

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
                startActivity(new Intent(this, DayViewActivity.class));
                return true;
            case R.id.action_speech_recognition:
                startActivity(new Intent(this, SpeechActivity.class));
                return true;
            case R.id.action_donut:
                startActivity(new Intent(this, DonutActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
