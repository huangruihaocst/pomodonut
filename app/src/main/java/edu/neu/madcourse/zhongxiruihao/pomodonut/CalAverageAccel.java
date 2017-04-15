package edu.neu.madcourse.zhongxiruihao.pomodonut;

import android.app.Service;
import android.support.v7.widget.LinearLayoutCompat;

import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.neu.madcourse.zhongxiruihao.pomodonut.DataStructure.PermanentDataPoint;
import edu.neu.madcourse.zhongxiruihao.pomodonut.DataStructure.TemporaryDataPoint;

/**
 * Created by Ben_Big on 4/15/17.
 */

public class CalAverageAccel {
    public static List<PermanentDataPoint> calAverage(List<TemporaryDataPoint> points, long currentTime){

        List<PermanentDataPoint> result=new ArrayList<>();
        if (points.size()==0){
            PermanentDataPoint point =new PermanentDataPoint(currentTime-AccelProcessService.INTERVAL ,0);
            result.add(point);
            return result;
        }

        TemporaryDataPoint[] pointsArray=new TemporaryDataPoint[points.size()];
        pointsArray=points.toArray(pointsArray);
        Arrays.sort(pointsArray,new Comparator<TemporaryDataPoint>(){
            @Override
            public int compare(TemporaryDataPoint a, TemporaryDataPoint b){
                return (int)(a.time-b.time);
            }
        });



        int currentStartingPoint=0;
        int currentSize=1;
        double cumulativeSum=pointsArray[currentStartingPoint].accelerationData;

        for (int i=0;i<pointsArray.length;i++){
            if (i==currentStartingPoint) continue;
            if ((pointsArray[i].time-pointsArray[currentStartingPoint].time)>AccelProcessService.INTERVAL){
                //Store the result so far
                PermanentDataPoint newPoint=new PermanentDataPoint(
                        pointsArray[currentStartingPoint].time, cumulativeSum/currentSize
                );
                result.add(newPoint);

                //Update the current starting point
                currentStartingPoint=i;
                currentSize=1;
                cumulativeSum=pointsArray[currentStartingPoint].accelerationData;
            }
            else{
                //Added to the current cumulative points
                currentSize++;
                cumulativeSum+=pointsArray[i].accelerationData;
            }

            if (i==pointsArray.length-1){
                //Store the result so far
                PermanentDataPoint newPoint=new PermanentDataPoint(
                        pointsArray[currentStartingPoint].time, cumulativeSum/currentSize
                );
                result.add(newPoint);
            }
        }
        return result;
    }


}
