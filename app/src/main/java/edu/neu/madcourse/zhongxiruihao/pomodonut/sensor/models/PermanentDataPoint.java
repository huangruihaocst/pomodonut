package edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.models;

import com.orm.SugarRecord;

/**
 * Created by Ben_Big on 4/15/17.
 */

public class PermanentDataPoint extends SugarRecord {

    public long time;
    public double accelerationData;

    public PermanentDataPoint(){
    }

    public PermanentDataPoint(long time, double accelerationData){
        this.time=time;
        this.accelerationData=accelerationData;
    }

}
