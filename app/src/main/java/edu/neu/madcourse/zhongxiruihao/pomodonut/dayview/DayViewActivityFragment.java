package edu.neu.madcourse.zhongxiruihao.pomodonut.dayview;

import android.graphics.Color;
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
import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.models.PermanentDataPoint;
import edu.neu.madcourse.zhongxiruihao.pomodonut.utils.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class DayViewActivityFragment extends Fragment {

    private static final int SECONDS_PER_DAY = 24 * 60 * 60;

    private static final int FAB_SIZE = 36;  // dp

    public DayViewActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_day_view, container, false);
        final WeekView weekView = (WeekView) root.findViewById(R.id.week_view);
        setEvents(weekView);

        weekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                Log.i("fab", "click");
                float top = eventRect.top;
                float bottom = eventRect.bottom;
                float left = eventRect.left;
                float right = eventRect.right;

                FloatingActionButton upUpFab = (FloatingActionButton) getActivity().findViewById(R.id.up_up_fab);
                setFabPosition(upUpFab, left + FAB_SIZE / 2, top + FAB_SIZE / 2);

                FloatingActionButton upDownFab = (FloatingActionButton) getActivity().findViewById(R.id.up_down_fab);
                setFabPosition(upDownFab, right - FAB_SIZE * 3, top + FAB_SIZE / 2);

                FloatingActionButton downUpFab = (FloatingActionButton) getActivity().findViewById(R.id.down_up_fab);
                setFabPosition(downUpFab, right - FAB_SIZE * 3, bottom + FAB_SIZE * 5);

                FloatingActionButton downDownFab = (FloatingActionButton) getActivity().findViewById(R.id.down_down_fab);
                setFabPosition(downDownFab, left + FAB_SIZE / 2, bottom + FAB_SIZE * 5);

            }
        });

        final ScrollView scrollView = (ScrollView) root.findViewById(R.id.scroll_view);
        setScrollable(scrollView, weekView);

        LineChart lineChart = (LineChart) root.findViewById(R.id.line_chart);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        setLineChart(lineChart, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        return root;
    }

    List<WeekViewEvent> getEvents(int year, int month) {
        List<WeekViewEvent> events = new ArrayList<>();
        WeekViewEvent event = new WeekViewEvent();

        event.setName("event name");

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 4);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, month - 1);
        startTime.set(Calendar.YEAR, year);
        event.setStartTime(startTime);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, month - 1);
        event.setEndTime(endTime);

        event.setColor(Color.BLUE);

        events.add(event);
        return events;
    }

    private void setEvents(WeekView weekView) {
        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                return getEvents(newYear, newMonth);
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

        // find accel data in the database whose time is between the above two unix time
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
        } else {
            weekView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    int scrollX = scrollView.getScrollX(); //for horizontalScrollView
                    int scrollY = scrollView.getScrollY(); //for verticalScrollView
                    scrollView.scrollTo(scrollX, scrollY);
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
}
