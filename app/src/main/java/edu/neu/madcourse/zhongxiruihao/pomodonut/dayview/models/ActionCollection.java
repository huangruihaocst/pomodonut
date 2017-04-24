package edu.neu.madcourse.zhongxiruihao.pomodonut.dayview.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangruihao on 4/24/17.
 */

public class ActionCollection {

    public int year;
    public int month;
    public List<Action> actions;

    ActionCollection() {
        actions = new ArrayList<>();
    }

    public ActionCollection(int year, int month, List<Action> actions) {
        this.year = year;
        this.month = month;
        this.actions = actions;
    }
}
