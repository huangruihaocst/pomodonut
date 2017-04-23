package edu.neu.madcourse.zhongxiruihao.pomodonut.editevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;
import edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.CountdownTimersFragment;

public class EditEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String eventName = data.getString(CountdownTimersFragment.EVENT_NAME_BUNDLE_KEY);
        long eventTime = data.getLong(CountdownTimersFragment.EVENT_TIME_BUNDLE_KEY);

        FragmentManager manager = getSupportFragmentManager();
        EditEventActivityFragment fragment = (EditEventActivityFragment) manager.findFragmentById(R.id.edit_event_fragment);
        fragment.setEvent(eventName, eventTime);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_save:
                // TODO: save this new event to database
                Toast.makeText(EditEventActivity.this, "saved", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
