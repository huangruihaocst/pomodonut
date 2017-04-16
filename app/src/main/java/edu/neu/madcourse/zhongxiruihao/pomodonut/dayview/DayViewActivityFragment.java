package edu.neu.madcourse.zhongxiruihao.pomodonut.dayview;

import android.graphics.Color;
import android.os.Build;
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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;
import edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.module.PermanentDataPoint;

/**
 * A placeholder fragment containing a simple view.
 */
public class DayViewActivityFragment extends Fragment {

    private static final int MINUTE_PER_DAY = 24 * 60;

    public DayViewActivityFragment() {
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
        setLineChart(lineChart, 2017, 4, 16);

        return root;
    }

    List<WeekViewEvent> getEvents(int year, int month) {
        List<WeekViewEvent> events = new ArrayList<>();
        WeekViewEvent event = new WeekViewEvent();

        event.setName("event name");

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, month - 1);
        startTime.set(Calendar.YEAR, year);
        event.setStartTime(startTime);

        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, month - 1);
        event.setEndTime(endTime);

        event.setColor(Color.RED);

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
        int recordPerDay = MINUTE_PER_DAY / recordFrequency;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date, 0, 0, 0);
        long startUTC = calendar.getTimeInMillis() + TimeZone.getDefault().getRawOffset();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        long endUTC = calendar.getTime().getTime() + TimeZone.getDefault().getRawOffset();

        List<PermanentDataPoint> permanentDataPointList = PermanentDataPoint
                .findWithQuery(PermanentDataPoint.class,
                        "select * from PERMANENT_DATA_POINT where time >= ? and time < ?",
                        String.valueOf(startUTC), String.valueOf(endUTC));
        Log.d("offset", "" + TimeZone.getDefault().getRawOffset());
        Log.d("timezone", calendar.getTimeZone().toString());
        Log.d("time", startUTC + " " + endUTC);
        Log.i("size", "" + permanentDataPointList.size());
        Random random = new Random();

        List<Entry> lineEntries = new ArrayList<>();
        for (int i = 0; i < 240; ++i) {
            lineEntries.add(new Entry(i, random.nextFloat() * 10));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "data set 1");
        lineDataSet.setLineWidth(0.1f);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(false);
        // style line data set here
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.setScaleEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawLabels(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMaximum(10);
        yAxis.setAxisMinimum(0);
        yAxis.setDrawLabels(false);
        yAxis = lineChart.getAxisRight();
        yAxis.setAxisMaximum(10);
        yAxis.setAxisMinimum(0);
        yAxis.setDrawLabels(false);
        lineChart.setDrawGridBackground(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
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
}
