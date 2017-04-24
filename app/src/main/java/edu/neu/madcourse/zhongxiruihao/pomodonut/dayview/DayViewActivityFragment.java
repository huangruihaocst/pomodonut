package edu.neu.madcourse.zhongxiruihao.pomodonut.dayview;

import android.graphics.RectF;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.orm.SugarContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;
import edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models.Event;
import edu.neu.madcourse.zhongxiruihao.pomodonut.dayview.models.Action;
import edu.neu.madcourse.zhongxiruihao.pomodonut.dayview.models.ActionCollection;
import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.models.PermanentDataPoint;
import edu.neu.madcourse.zhongxiruihao.pomodonut.utils.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class DayViewActivityFragment extends Fragment {

    private static final int SECONDS_PER_DAY = 24 * 60 * 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MILLISECONDS_PER_SECOND = 1000;

    private FloatingActionButton upUpFab;
    private FloatingActionButton upDownFab;
    private FloatingActionButton downUpFab;
    private FloatingActionButton downDownFab;

    public DayViewActivityFragment() {}

    List<ActionCollection> actionCollections;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(getActivity());
        actionCollections = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_day_view, container, false);

        final WeekView weekView = (WeekView) root.findViewById(R.id.week_view);
        setEvents(weekView);

        final ScrollView scrollView = (ScrollView) root.findViewById(R.id.scroll_view);
        setScrollable(scrollView, weekView);

        LineChart lineChart = (LineChart) root.findViewById(R.id.line_chart);
        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        setLineChart(lineChart, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        weekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(final WeekViewEvent event, final RectF eventRect) {
                float top = eventRect.top;
                float bottom = eventRect.bottom;
                float left = eventRect.left;
                float right = eventRect.right;

                final float clickedX = (left + right) / 2;
                final float clickedY = (top + bottom) / 2;

                upUpFab = (FloatingActionButton) getActivity().findViewById(R.id.up_up_fab);
                upDownFab = (FloatingActionButton) getActivity().findViewById(R.id.up_down_fab);
                downUpFab = (FloatingActionButton) getActivity().findViewById(R.id.down_up_fab);
                downDownFab = (FloatingActionButton) getActivity().findViewById(R.id.down_down_fab);

                setFabPosition(upUpFab, left, top - scrollView.getScrollY());
                setFabPosition(upDownFab, right - upDownFab.getWidth(), top - scrollView.getScrollY());
                setFabPosition(downUpFab, right - downUpFab.getWidth(), bottom + 2 * downUpFab.getHeight() - scrollView.getScrollY());
                setFabPosition(downDownFab, left, bottom + 2 * downDownFab.getHeight() - scrollView.getScrollY());

                upUpFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar startTime = event.getStartTime();
                        calendar.setTimeZone(TimeZone.getDefault());
                        event.setStartTime(startTime);
                        for (ActionCollection collection: actionCollections) {
                            if (startTime.get(Calendar.YEAR) == collection.year
                                    && startTime.get(Calendar.MONTH) + 1 == collection.month) {
                                List<Action> actions = collection.actions;
                                for (Action action: actions) {
                                    if (action.startTime == event.getStartTime().getTimeInMillis()) {
                                        action.startTime -= getResources().getInteger(R.integer.adjust_accuracy)
                                                * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
                                        hideFabs();
                                        // TODO: write database here
                                        break;
                                    }
                                }
                            }
                        }
                        weekView.notifyDatasetChanged();
                    }
                });

                upDownFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar startTime = event.getStartTime();
                        calendar.setTimeZone(TimeZone.getDefault());
                        event.setStartTime(startTime);
                        for (ActionCollection collection: actionCollections) {
                            if (startTime.get(Calendar.YEAR) == collection.year
                                    && startTime.get(Calendar.MONTH) + 1 == collection.month) {
                                List<Action> actions = collection.actions;
                                for (Action action: actions) {
                                    if (action.startTime == event.getStartTime().getTimeInMillis()) {
                                        if (action.startTime + getResources().getInteger(R.integer.adjust_accuracy)
                                                * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND < action.endTime) {
                                            action.startTime += getResources().getInteger(R.integer.adjust_accuracy)
                                                    * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
                                            hideFabs();
                                            // TODO: write database here
                                        } else {
                                            Toast.makeText(getActivity(), getString(R.string.toast_start_time_bigger_than_end_time),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                        weekView.notifyDatasetChanged();
                    }
                });

                downUpFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar endTime = event.getEndTime();
                        calendar.setTimeZone(TimeZone.getDefault());
                        event.setEndTime(endTime);
                        for (ActionCollection collection: actionCollections) {
                            if (endTime.get(Calendar.YEAR) == collection.year
                                    && endTime.get(Calendar.MONTH) + 1 == collection.month) {
                                List<Action> actions = collection.actions;
                                for (Action action: actions) {
                                    if (action.endTime == event.getEndTime().getTimeInMillis()) {
                                        if (action.endTime - getResources().getInteger(R.integer.adjust_accuracy)
                                                * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND > action.startTime) {
                                            action.endTime -= getResources().getInteger(R.integer.adjust_accuracy)
                                                    * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
                                            hideFabs();
                                            // TODO: write database here
                                        } else {
                                            Toast.makeText(getActivity(), getString(R.string.toast_start_time_bigger_than_end_time),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }
                        }
                        weekView.notifyDatasetChanged();
                    }
                });

                downDownFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar endTime = event.getEndTime();
                        calendar.setTimeZone(TimeZone.getDefault());
                        event.setEndTime(endTime);
                        for (ActionCollection collection: actionCollections) {
                            if (endTime.get(Calendar.YEAR) == collection.year
                                    && endTime.get(Calendar.MONTH) + 1 == collection.month) {
                                List<Action> actions = collection.actions;
                                for (Action action: actions) {
                                    if (action.endTime == event.getEndTime().getTimeInMillis()) {
                                        action.endTime += getResources().getInteger(R.integer.adjust_accuracy)
                                                * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
                                        hideFabs();
                                        // TODO: write database here
                                        break;
                                    }
                                }
                            }
                        }
                        weekView.notifyDatasetChanged();
                    }
                });
            }
        });

        return root;
    }

    // read events for (year, month) from the database and update actionCollections
    // month is 1 based
    List<Action> getActions(int year, int month) {
        for (ActionCollection collection: actionCollections) {
            if (year == collection.year && month == collection.month) {
                return collection.actions;
            }
        }

        List<Action> actions = new ArrayList<>();
        // TODO: read actions from database

        // dummy actions
        Action action = new Action();
        action.event = new Event("event name", 60 * 1000);
        action.startTime = 1493042400000L;  // 2017.4.24-10:00:00
        action.endTime = 1493042400000L + 2 * 60 * 60 * 1000;  // 2017.4.24-12:00:00
        actions.add(action);

        actionCollections.add(new ActionCollection(year, month, actions));

        return actions;
    }

    private void setEvents(WeekView weekView) {
        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                List<Action> actions = getActions(newYear, newMonth);
                List<WeekViewEvent> events = new ArrayList<>();
                ArrayList<Integer> colors = Utils.getColors();
                // change format: one action corresponds to one event
                for (Action action: actions) {
                    WeekViewEvent event = new WeekViewEvent();
                    event.setName(action.event.name);
                    Calendar startTime = Calendar.getInstance(Locale.getDefault());
                    startTime.setTimeInMillis(action.startTime);
                    event.setStartTime(startTime);
                    Calendar endTime = (Calendar) startTime.clone();
                    endTime.setTimeInMillis(action.endTime);
                    event.setEndTime(endTime);
                    event.setColor(colors.get(actions.indexOf(action)));
                    // WeekView doesn't check year and month Orz
                    if(startTime.get(Calendar.YEAR) == newYear
                            && startTime.get(Calendar.MONTH) + 1 == newMonth) {
                        events.add(event);
                    }
                }
                return events;
            }
        });
    }

    // year, month, date in current timezone
    private void setLineChart(LineChart lineChart, int year, int month, int date) {
        int recordFrequency = getContext().getResources().getInteger(R.integer.record_accel_frequency);
        int recordPerDay = SECONDS_PER_DAY / recordFrequency;

        // get the unix time of local start time and end time of a day
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.getDefault());
        String dateString = String.format(Locale.US, "%04d", year) + "-"
                + String.format(Locale.US, "%2d", month) + "-"
                + String.format(Locale.US, "%2d", date) + "T"
                + "00:00:00Z";
        long startUnix = 0, endUnix = 0;
        try {
            Date d = simpleDateFormat.parse(dateString);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            startUnix = d.getTime();
            dateString = String.format(Locale.US, "%04d", year) + "-"
                    + String.format(Locale.US, "%2d", month) + "-"
                    + String.format(Locale.US, "%2d", date) + "T"
                    + "23:59:59Z";
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            d = simpleDateFormat.parse(dateString);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            endUnix = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // find accel data in the database whose time is between the above two unix duration
        List<PermanentDataPoint> permanentDataPointList = PermanentDataPoint
                .findWithQuery(PermanentDataPoint.class,
                        "select * from PERMANENT_DATA_POINT where time >= ? and time < ?",
                        String.valueOf(startUnix), String.valueOf(endUnix));

        List<Entry> lineEntries = new ArrayList<>();
        for (int i = 0; i < recordPerDay; ++i) {
            lineEntries.add(new Entry(i, 0));
        }
        for (PermanentDataPoint permanentDataPoint: permanentDataPointList) {
            if (permanentDataPoint.accelerationData != 0) {
                double portion = (double) (permanentDataPoint.time - startUnix) / (endUnix - startUnix);
                int index = (int) (portion * recordPerDay);
                lineEntries.set(index, new Entry(index, (float) permanentDataPoint.accelerationData));
            }
        }

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Accel Data");
        setLineDataSetStyle(lineDataSet);
        LineData lineData = new LineData(lineDataSet);
        setLineChartAxisStyle(lineChart);
        lineChart.setData(lineData);
        setLineChartStyle(lineChart);
        lineChart.invalidate();
    }

    private void setScrollable(final ScrollView scrollView, WeekView weekView) {
        if (Build.VERSION.SDK_INT >= 23) {
            weekView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    scrollView.scrollTo(scrollX, scrollY);
                }
            });
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    hideFabs();
                }
            });
        } else {
            weekView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    int scrollX = scrollView.getScrollX();  //for horizontalScrollView
                    int scrollY = scrollView.getScrollY();  //for verticalScrollView
                    scrollView.scrollTo(scrollX, scrollY);
                }
            });
            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    hideFabs();
                }
            });
        }
    }

    private void setLineDataSetStyle(LineDataSet dataSet) {
        dataSet.setLineWidth(0.1f);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(false);
    }

    private void setLineChartStyle(LineChart chart) {
        chart.setScaleEnabled(false);
        chart.setDrawGridBackground(false);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
    }

    private void setLineChartAxisStyle(LineChart chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setEnabled(false);
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMaximum(10);
        yAxis.setAxisMinimum(0);
        yAxis.setDrawLabels(false);
        yAxis = chart.getAxisRight();
        yAxis.setAxisMaximum(10);
        yAxis.setAxisMinimum(0);
        yAxis.setDrawLabels(false);
    }

    private void setFabPosition(FloatingActionButton fab, float x, float y) {
        fab.setVisibility(View.VISIBLE);
        fab.setX(x);
        fab.setY(y);
    }

    private void hideFabs() {
        if (upUpFab != null) {
            upUpFab.setVisibility(View.GONE);
        }
        if (upDownFab != null) {
            upDownFab.setVisibility(View.GONE);
        }
        if (downUpFab != null) {
            downUpFab.setVisibility(View.GONE);
        }
        if (downDownFab != null) {
            downDownFab.setVisibility(View.GONE);
        }
    }
}
