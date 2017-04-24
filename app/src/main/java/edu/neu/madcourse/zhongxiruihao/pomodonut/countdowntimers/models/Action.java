package edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models;

import com.orm.SugarRecord;

/**
 * Created by huangruihao on 4/22/17.
 */

public class Action extends SugarRecord {

    public Event event;

    public long startTime;
    public long endTime;

    public Action() {}

    public Action(Event event) {
        this.event = event;
    }


    public void setStartTime(long startTime){this.startTime=startTime;}

    public void setEndTime(long endTime){this.endTime=endTime;}

}
