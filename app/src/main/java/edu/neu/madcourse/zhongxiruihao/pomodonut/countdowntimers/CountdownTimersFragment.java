package edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;
import edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models.Event;
import edu.neu.madcourse.zhongxiruihao.pomodonut.editevent.EditEventActivity;
import edu.neu.madcourse.zhongxiruihao.pomodonut.utils.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class CountdownTimersFragment extends Fragment {

    private static final String EVENTS_BUNDLE_KEY = "events bundle key";
    private static final String PAGE_BUNDLE_KEY = "page bundle key";

    private Event[] events;
    public int page;  // page index

    private static int timerIds[] = {R.id.timer_0, R.id.timer_1, R.id.timer_2,
            R.id.timer_3, R.id.timer_4, R.id.timer_5};

    public CountdownTimersFragment() {}

    public static CountdownTimersFragment newInstance(Event[] events, int page) {
        CountdownTimersFragment fragment = new CountdownTimersFragment();
        Bundle args = new Bundle();
        args.putSerializable(EVENTS_BUNDLE_KEY ,events);
        args.putInt(PAGE_BUNDLE_KEY, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            events = (Event[]) getArguments().getSerializable(EVENTS_BUNDLE_KEY);
            page = getArguments().getInt(PAGE_BUNDLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_countdown_timers, container, false);
        View[] timers = new View[MainActivity.TIMERS_PER_PAGE];
        for (int i = 0; i < timers.length; ++i) {
            timers[i] = root.findViewById(timerIds[i]);
        }
        if (events != null) {
            int count = events.length;
            for (int i = 0; i < timers.length; ++i) {
                if (i >= count) {
                    timers[i].setVisibility(View.GONE);
                } else {
                    ((TextView) timers[i].findViewById(R.id.text_timer_title)).setText(events[i].name);
                    ((TextView) timers[i].findViewById(R.id.text_timer_time))
                            .setText(Utils.formatMillisecond(events[i].time));
                    ArrayList<Integer> colors = Utils.getColors();
                    LayerDrawable timerBackground = (LayerDrawable) timers[i].getBackground();
                    GradientDrawable circle = (GradientDrawable) timerBackground.findDrawableByLayerId(R.id.background_circle);
                    circle.setStroke(Utils.dpToPx(getContext(), 2),
                            colors.get(page * MainActivity.TIMERS_PER_PAGE + i));
                    GradientDrawable line = (GradientDrawable) timerBackground.findDrawableByLayerId(R.id.background_line);
                    line.setStroke(Utils.dpToPx(getContext(), 1),
                            colors.get(page * MainActivity.TIMERS_PER_PAGE + i));
                }
            }
        } else {  // disable all
            for (View timer: timers) {
                timer.setVisibility(View.GONE);
            }
        }

        root.findViewById(R.id.button_timer_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditEventActivity.class));
            }
        });

        return root;
    }
}
