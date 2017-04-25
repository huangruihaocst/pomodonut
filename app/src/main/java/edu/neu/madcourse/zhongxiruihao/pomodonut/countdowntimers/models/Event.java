package edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by huangruihao on 4/22/17.
 */

/**
 * Event is not the same as WeekViewEvent.
 * It is repeated action.
 * That is, if several actions have the same name, they are the same event.
 */
public class Event extends SugarRecord implements Serializable {

    public String name;
    // duration supposed to use in millisecond
    public long duration;

    // times this event happens
    private int frequency;

    public Event() {}

    public int getFrequency() {
        return frequency;
    }

    public String getName(){ return name;}

    public Event(String name, long duration) {
        this.name = name;
        this.duration = duration;
    }



}
