package edu.neu.madcourse.zhongxiruihao.pomodonut.editevent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditEventActivityFragment extends Fragment {

    public EditEventActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_event, container, false);
    }
}
