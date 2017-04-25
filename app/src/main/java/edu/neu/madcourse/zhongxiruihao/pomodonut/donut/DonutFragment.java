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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models.Event;
import edu.neu.madcourse.zhongxiruihao.pomodonut.dayview.models.Action;
import edu.neu.madcourse.zhongxiruihao.pomodonut.donut.DonutActivity.viewType;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

/**
 * Created by Ben_Big on 4/23/17.
 */

public class DonutFragment extends Fragment {
    private int differenceFromCurrentTime;
    private viewType type;
    private ArrayList<Integer> colors;
    private DateFormat formatter;
    private EventAndDuration[] eventAndDurations;
    private static final int NUM_EVENTS_IN_PIE=8;


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


        eventAndDurations=getTopEventDuration(findActionsOfPresentDay(),NUM_EVENTS_IN_PIE);

        /*
        try {
            Event event = new Event("Test", 32);
            event.save();
            Event bloodyEvent=new Event("Bloody Test",32);
            bloodyEvent.save();

            Action action=new Action(bloodyEvent);
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
        }*/
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
        PieDataSet dataSet = new PieDataSet(yvalues, "");


        if (eventAndDurations==null || eventAndDurations.length==0){

            yvalues.add(new PieEntry(1f,"???"));
            dataSet.setColor(Color.rgb(160,160,160));
            dataSet.setDrawValues(false);
        }
        else{

            for (int i=0;i<eventAndDurations.length;i++){
                yvalues.add(new PieEntry(eventAndDurations[i].getDuration(),
                        eventAndDurations[i].getEventName()));
            }
            dataSet.setColors(colors);

        }






        PieData data = new PieData(dataSet);
        data.setValueTextSize(24f);
        data.setValueTextColor(Color.WHITE);


        pieChart.setData(data);
        pieChart.setEntryLabelTextSize(24f);

        Legend l=pieChart.getLegend();
        l.setEnabled(false);
        Description description=new Description();
        description.setText("");
        pieChart.setDescription(description);

        pieChart.setRotationEnabled(true);


        pieChart.setCenterText(getCurrentday());



        pieChart.setCenterTextSize(21f);

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

            return findActionsBetweenTwoTimes(firstSecondOfPresentDay,lastSecondOfPresentDay);
        }
        catch (ParseException e){
            String err=e.toString();
            return null;
        }
    }


    private List<Action> findActionsBetweenTwoTimes(long startTime, long endTime){
        if (startTime>=endTime) return null;
        List<Action> actionsInBetween= Action.find(Action.class, " START_TIME >= ? and END_TIME <= ?", ""+startTime, ""+endTime);
        List<Action> actionsWithEalierStartTime=Action.find(Action.class, " START_TIME < ? and  END_TIME >= ? and END_TIME <= ?", ""+startTime,""+startTime, ""+endTime);
        List<Action> actionsWithLaterEndTime=Action.find(Action.class, " START_TIME >= ? and  START_TIME <= ? and END_TIME >= ?", ""+startTime, ""+endTime,""+endTime);

        for (Action action : actionsWithEalierStartTime){
            action.setStartTime(startTime);
            actionsInBetween.add(action);
        }

        for (Action action: actionsWithLaterEndTime){
            action.setEndTime(endTime);
            actionsInBetween.add(action);
        }

        return actionsInBetween;
    }


    private EventAndDuration[] getTopEventDuration(List<Action> actions, int desiredSize){
        HashMap<String, Long> eventNameToDuration=new HashMap<>();
        for (Action action : actions){
            String eventName=action.getEvent().getName();
            if (eventNameToDuration.containsKey(eventName)){
                long oldDuration=eventNameToDuration.get(action.getEvent().getName());
                long newDuration=oldDuration+(action.getEndTime()-action.getStartTime());
                eventNameToDuration.put(eventName,newDuration);
            }
            else{
                long newDuration=action.getEndTime()-action.getStartTime();
                eventNameToDuration.put(eventName,newDuration);
            }
        }

        EventAndDuration[] arr=new EventAndDuration[eventNameToDuration.size()];
        int index=0;

        for (String key: eventNameToDuration.keySet()){
            arr[index]=new EventAndDuration(key,eventNameToDuration.get(key));
            index++;
        }

        Arrays.sort(arr, new Comparator<EventAndDuration>() {
            @Override
            public int compare(EventAndDuration t1, EventAndDuration t2) {
                return (int)(t1.getDuration()-t2.getDuration());
            }
        });
        if (desiredSize>=arr.length){return arr;}
        else{
            EventAndDuration[] result=new EventAndDuration[desiredSize];
            for (int i=0;i<desiredSize;i++){
                result[i]=arr[i];
            }
            return result;
        }
    }





    @Override
    public void onDestroy(){
        super.onDestroy();
       // Action.deleteAll(Action.class);
       // Event.deleteAll(Event.class);

    }



    private class EventAndDuration{
        String eventName;
        long duration;

        EventAndDuration(String eventName,long duration){
            this.eventName=eventName;
            this.duration=duration;
        }

        String getEventName(){return eventName;}
        long getDuration(){return duration;}
    }



}
