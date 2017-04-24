package edu.neu.madcourse.zhongxiruihao.pomodonut.donut;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import edu.neu.madcourse.zhongxiruihao.pomodonut.donut.DonutActivity.viewType;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

/**
 * Created by Ben_Big on 4/23/17.
 */

public class DonutFragment extends Fragment {
    private int differenceFromCurrentTime;
    private viewType type;
    private ArrayList<Integer> colors;

    public void init(int difference, viewType type){
        this.differenceFromCurrentTime=difference;
        this.type=type;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        colors = new ArrayList<>();

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

        //Toast.makeText(getActivity(), getCurrentday(), Toast.LENGTH_SHORT).show();

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

        dataSet.setColors(colors);


        PieData data = new PieData(dataSet);
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.setEntryLabelTextSize(16f);

        Legend l=pieChart.getLegend();
        l.setEnabled(false);
        Description description=new Description();
        description.setText("");
        pieChart.setDescription(description);

        pieChart.setRotationEnabled(true);

        pieChart.setCenterText(getCurrentday());

        //Description description=new Description();
        //description.setText("Test");
        //description.setPosition(200,160);
        //pieChart.setDescription(description);

        //Legend l = pieChart.getLegend();
        //l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        //l.setXEntrySpace(7);
        //l.setYEntrySpace(5);

    }


    private String getCurrentday(){
        /*
        Calendar cal= Calendar.getInstance();
        int dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
        int monthOfYear=cal.get(Calendar.MONTH);

        String result=""+monthOfYear+" "+dayOfMonth;
        return result;*/
        Date today=new Date();
        Date theOtherDay=new Date(today.getTime()-differenceFromCurrentTime*60*60*24*1000);
        return theOtherDay.toString();

    }



}