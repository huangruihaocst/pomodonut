package edu.neu.madcourse.zhongxiruihao.pomodonut.countdowntimers.models;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by huangruihao on 4/22/17.
 */

public class Event extends SugarRecord implements Serializable {

    public String name;
    // time supposed to use in millisecond
    public long time;

    // times this event happens
    private int frequency;

    public Event() {}

    public int getFrequency() {
        return frequency;
    }

    public Event(String name, long time) {
        this.name = name;
        this.time = time;
    }

}
