package com.ruihaohuang.chartsdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();

//        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);
//        List<Entry> entries = new ArrayList<>();
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
//        lineChart.invalidate();

        PieChart pieChart = (PieChart) findViewById(R.id.pie_chart);
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0;i < 5; ++i) {
            entries.add(new PieEntry(random.nextFloat() * 5, "Label" + i));
        }
        PieDataSet pieDataSet = new PieDataSet(entries, "data set 2");
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(16f);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        Description description = new Description();
        description.setText("first pie chart");
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setCenterText("Center Text");
        pieChart.setEntryLabelTextSize(16f);
        pieChart.setUsePercentValues(true);
        pieChart.setDescription(description);
        pieChart.invalidate();
    }
}
