package edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.module;

import com.orm.SugarRecord;

/**
 * Created by Ben_Big on 4/15/17.
 */

public class PermanentDataPoint extends SugarRecord {

    long time;
    double accelerationData;

    public PermanentDataPoint(){
    }

    public PermanentDataPoint(long time, double accelerationData){
        this.time=time;
        this.accelerationData=accelerationData;
    }

}
