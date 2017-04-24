package edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by huangruihao on 4/22/17.
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

    public Event(String name, long duration) {
        this.name = name;
        this.duration = duration;
    }

}
