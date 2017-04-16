package edu.neu.madcourse.zhongxiruihao.pomodonut.sensor.module;

import com.orm.SugarRecord;

/**
 * Created by Ben_Big on 4/15/17.
 */
public class TemporaryDataPoint extends SugarRecord {
    public long time;
    public double accelerationData;

    public TemporaryDataPoint(){
    }

    public TemporaryDataPoint(long time, double accelerationData){
        this.time=time;
        this.accelerationData=accelerationData;
    }

}