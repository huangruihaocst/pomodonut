package com.ruihaohuang.dayviewdemo;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WeekView weekView = (WeekView) findViewById(R.id.weekView);
        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                return getEvents(newYear, newMonth);
            }
        });

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

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


//        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);
//
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//
//        lineChart.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT / 2));
//
//        List<Entry> entries = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 10; ++i) {
//            entries.add(new Entry(i, random.nextFloat() * 10));
//        }
//        LineDataSet lineDataSet = new LineDataSet(entries, "data set 1");
//        // style line data set here
//        LineData lineData = new LineData(lineDataSet);
//        lineChart.setData(lineData);
//        Description description = new Description();
//        description.setText("first line chart");
//        lineChart.setDescription(description);
//        lineChart.setScaleEnabled(false);
//        YAxis yAxis = lineChart.getAxisLeft();
//        yAxis.setAxisMaximum(10);
//        yAxis.setAxisMinimum(0);
//        yAxis.setDrawGridLines(false);
//        lineChart.getXAxis().setDrawGridLines(false);
//        lineChart.invalidate();
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
