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
    private static final String EVENT_TIME_ARGS_KEY = "event time args key";

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

        int maxHour = getResources().getInteger(R.integer.hours_picker_max_hour);
        String[] hours = new String[maxHour + 1];
        for (int i = 0; i < maxHour + 1; ++i) {
            hours[i] = String.valueOf(i);
        }
        hoursPicker.setDisplayedValues(hours);
        hoursPicker.setWrapSelectorWheel(false);
        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(maxHour);
        hoursPicker.setValue(0);

        return root;
    }

    void setEvent(String eventName, long eventTime) {
        this.eventName = eventName;
        this.eventTime = eventTime;
    }
}
