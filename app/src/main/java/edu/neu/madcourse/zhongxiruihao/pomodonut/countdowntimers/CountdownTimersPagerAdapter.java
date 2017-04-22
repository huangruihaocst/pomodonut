package edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by huangruihao on 4/15/17.
 */

class CountdownTimersPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CountdownTimersFragment> fragments;

    CountdownTimersPagerAdapter(FragmentManager fm, ArrayList<CountdownTimersFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
