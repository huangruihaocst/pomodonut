package edu.neu.madcourse.zhongxiruihao.pomodonut.SQLite_Demo;

import com.orm.SugarRecord;

/**
 * Created by Ben_Big on 4/10/17.
 */

public class Book extends SugarRecord {
    String title;
    String edition;

    public Book(){

    }
    public Book(String title, String edition){
        this.title=title;
        this.edition=edition;
    }
}
