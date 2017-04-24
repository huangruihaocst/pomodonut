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
import com.orm.SugarContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models.Action;
import edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models.Event;
import edu.neu.madcourse.zhongxiruihao.pomodonut.donut.DonutActivity.viewType;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

/**
 * Created by Ben_Big on 4/23/17.
 */

public class DonutFragment extends Fragment {
    private int differenceFromCurrentTime;
    private viewType type;
    private ArrayList<Integer> colors;
    DateFormat formatter;
    List<Action> listOfActions;


    public void init(int difference, viewType type){
        this.differenceFromCurrentTime=difference;
        this.type=type;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SugarContext.init(getActivity());

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

        formatter=new SimpleDateFormat("dd/MM/yyyy");



        listOfActions=findActionsOfPresentDay();

        try {
            Event event = new Event("Test", 32);
            event.save();

            Action action=new Action(event);
            action.setStartTime(1493063860983L);
            action.setEndTime(1493063860993L);
            action.save();


            Action action2=new Action(event);
            action2.setStartTime(1492053860983L);
            action2.setEndTime(1492063860993L);
            action2.save();


        }
        catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }


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


        if (listOfActions!=null && !listOfActions.isEmpty()) {
            pieChart.setCenterText("" + listOfActions.get(0));
        }
        else{
            pieChart.setCenterText("");

        }
        pieChart.setCenterTextSize(15f);

    }


    private String getCurrentday(){
        Date today=new Date();
        Date theOtherDay=new Date(today.getTime()-differenceFromCurrentTime*60*60*24*1000);
        return formatter.format(theOtherDay);

    }


    private List<Action> findActionsOfPresentDay(){
        Date today=new Date();
        Date presentDay=new Date(today.getTime()-differenceFromCurrentTime*60*60*24*1000);
        long firstSecondOfPresentDay;
        long lastSecondOfPresentDay;
        try{
            firstSecondOfPresentDay=formatter.parse(formatter.format(presentDay)).getTime();
            lastSecondOfPresentDay=firstSecondOfPresentDay+60*60*24*1000-1;
            //List<Action> actionsOfPresentDay=Action.listAll(Action.class);

        }
        catch (ParseException e){
            String err=e.toString();
            return null;
        }
       // List<Action> actionsOfPresentDay=Action.findWithQuery(Action.class, "Select * from ACTION where START_TIME >= ? and END_TIME <= ?", ""+firstSecondOfPresentDay, ""+lastSecondOfPresentDay);
        List<Action> actionsOfPresentDay=Action.find(Action.class, " START_TIME >= ? and END_TIME <= ?", ""+firstSecondOfPresentDay, ""+lastSecondOfPresentDay);
       // List<Action> actionsOfPresentDay=Action.find(Action.class, " START_TIME >= ?", ""+firstSecondOfPresentDay);

        return actionsOfPresentDay;
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
      //  Action.deleteAll(Action.class);
      //  Event.deleteAll(Event.class);

    }



}
