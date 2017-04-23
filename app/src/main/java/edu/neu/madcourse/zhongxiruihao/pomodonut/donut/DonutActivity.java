package edu.neu.madcourse.zhongxiruihao.pomodonut.donut;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

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

}
