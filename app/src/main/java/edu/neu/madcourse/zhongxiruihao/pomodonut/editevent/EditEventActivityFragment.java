package edu.neu.madcourse.zhongxiruihao.pomodonut.editevent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditEventActivityFragment extends Fragment {

    private static final String EVENT_NAME_ARGS_KEY = "event name args key";
    private static final String EVENT_TIME_ARGS_KEY = "event duration args key";

    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;

    private String eventName;
    private long eventTime;

    public static EditEventActivityFragment newInstance(String eventName, long eventTime) {
        EditEventActivityFragment fragment = new EditEventActivityFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_NAME_ARGS_KEY, eventName);
        args.putLong(EVENT_TIME_ARGS_KEY, eventTime);
        fragment.setArguments(args);
        return fragment;
    }

    public EditEventActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventName = getArguments().getString(EVENT_NAME_ARGS_KEY);
            eventTime = getArguments().getLong(EVENT_TIME_ARGS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_event, container, false);

        EditText editText = (EditText) root.findViewById(R.id.edit_event_name);
        editText.setText(eventName);

        NumberPicker hoursPicker = (NumberPicker) root.findViewById(R.id.picker_hours);
        NumberPicker minutesPicker = (NumberPicker) root.findViewById(R.id.picker_minutes);
        NumberPicker secondsPicker = (NumberPicker) root.findViewById(R.id.picker_seconds);

        setPicker(hoursPicker, getResources().getInteger(R.integer.hours_picker_max_hour), false);
        setPicker(minutesPicker, MINUTES_PER_HOUR, true);
        setPicker(secondsPicker, SECONDS_PER_MINUTE, true);

        return root;
    }

    private void setPicker(NumberPicker picker, int maxValue, boolean allowWrapSelectorWheel) {
        String[] hours = new String[maxValue + 1];
        for (int i = 0; i < maxValue + 1; ++i) {
            hours[i] = String.valueOf(i);
        }
        picker.setDisplayedValues(hours);
        picker.setWrapSelectorWheel(allowWrapSelectorWheel);
        picker.setMinValue(0);
        picker.setMaxValue(maxValue);
        picker.setValue(0);
    }

}
