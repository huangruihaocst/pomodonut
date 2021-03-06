package edu.neu.madcourse.zhongxiruihao.pomodonut.dayview.models;

import com.orm.SugarRecord;

import edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models.Event;

/**
 * Created by huangruihao on 4/22/17.
 */

/**
 * Action is another form of WeekViewEvent.
 * They are of almost the same meaning, but has different data structures.
 */
public class Action extends SugarRecord {

    public Event event;
    public long startTime;
    public long endTime;

    public Action() {}

    public Action(Event event) {
        this.event = event;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Event getEvent(){
        return event;
    }



}
