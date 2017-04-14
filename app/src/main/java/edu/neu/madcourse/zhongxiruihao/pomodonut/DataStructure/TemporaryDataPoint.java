package edu.neu.madcourse.zhongxiruihao.pomodonut.DataStructure;

import com.orm.SugarRecord;

/**
 * Created by Ben_Big on 4/13/17.
 */

public class TemporaryDataPoint extends SugarRecord {
    long time;
    double accelerationData;

    public TemporaryDataPoint(){
    }

    public TemporaryDataPoint(long time, double accelerationData){
        this.time=time;
        this.accelerationData=accelerationData;
    }

}
