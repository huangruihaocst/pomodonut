package edu.neu.madcourse.zhongxiruihao.pomodonut.dayview;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DayViewActivityFragment extends Fragment {

    public DayViewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_view, container, false);
    }
}
