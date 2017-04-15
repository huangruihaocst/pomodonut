package edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by huangruihao on 4/15/17.
 */

class CountdownTimersPagerAdapter extends FragmentStatePagerAdapter {

    private int pageCount;
    private Fragment[] fragments;

    CountdownTimersPagerAdapter(FragmentManager fm, int pageCount, Fragment[] fragments) {
        super(fm);
        this.pageCount = pageCount;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
