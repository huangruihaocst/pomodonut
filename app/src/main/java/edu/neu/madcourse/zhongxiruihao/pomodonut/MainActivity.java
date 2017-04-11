package edu.neu.madcourse.zhongxiruihao.pomodonut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.orm.SugarContext;

import edu.neu.madcourse.zhongxiruihao.pomodonut.SQLite_Demo.Book;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // SugarContext.init(this);
    }


    public void startService(View view){
       /* Book book=new Book("Title here","2nd edition");
        book.save();*/
        Log.d("abc","Start Service");
        startService(new Intent(getBaseContext(),MyService.class));
    }

    public void stopService(View view){
        stopService(new Intent(getBaseContext(),MyService.class));
    }


}
