package edu.neu.madcourse.zhongxiruihao.pomodonut.dayview;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

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
        View root = inflater.inflate(R.layout.fragment_day_view, container, false);
        final WeekView weekView = (WeekView) root.findViewById(R.id.week_view);
        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                return getEvents(newYear, newMonth);
            }
        });

        final ScrollView scrollView = (ScrollView) root.findViewById(R.id.scroll_view);

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

        Random random = new Random();

        LineChart lineChart = (LineChart) root.findViewById(R.id.line_chart);
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
}
