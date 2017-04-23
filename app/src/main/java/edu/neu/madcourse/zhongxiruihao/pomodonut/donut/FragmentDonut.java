package edu.neu.madcourse.zhongxiruihao.pomodonut.donut;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

/**
 * Created by Ben_Big on 4/23/17.
 */

public class FragmentDonut extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView=inflater.inflate(R.layout.fragment_donut,container,false);
        PieChart pieChart=(PieChart) rootView.findViewById(R.id.donut);
        initPieChart(pieChart);

        return rootView;
    }

    private void initPieChart(PieChart pieChart){
        List<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(8f, "Jan"));
        yvalues.add(new PieEntry(15f, "Feb"));
        yvalues.add(new PieEntry(12f, "March"));
        yvalues.add(new PieEntry(25f, "April "));
        yvalues.add(new PieEntry(23f, "May"));
        yvalues.add(new PieEntry(17f, "June"));
        yvalues.add(new PieEntry(18f, "July"));
        yvalues.add(new PieEntry(21f, "August"));
        yvalues.add(new PieEntry(21f, "Sep"));
        yvalues.add(new PieEntry(21f, "Oct"));
        yvalues.add(new PieEntry(21f, "Noc"));


        PieDataSet dataSet = new PieDataSet(yvalues, "");



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

        dataSet.setColors(colors);




        PieData data = new PieData(dataSet);
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.setEntryLabelTextSize(16f);

        //Description description=new Description();
        //description.setText("Test");
        //description.setPosition(200,160);
        //pieChart.setDescription(description);
        pieChart.setRotationEnabled(false);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);



       // Legend l = pieChart.getLegend();
       // l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
       // l.setXEntrySpace(7f);
       // l.setYEntrySpace(5f);
       // l.setYOffset(0f);


    }

}
