package edu.neu.madcourse.zhongxiruihao.pomodonut.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import edu.neu.madcourse.zhongxiruihao.pomodonut.R;

/**
 * Created by Ben_Big on 4/22/17.
 */

public class TimerNotification {

    NotificationManager notificationManager;
    NotificationCompat.Builder builder;


    public TimerNotification(NotificationManager notificationManager){
        this.notificationManager=notificationManager;
    }


    public void makeNotification(Context context, Intent intent, String title, String text){

        builder=new NotificationCompat.Builder(context);
        builder.setContentText(text);
        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.ic_timer_white_36dp);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setOnlyAlertOnce(true);
        builder.setContentIntent(PendingIntent.getActivity(context,10,intent,0));

        notificationManager.notify(0,builder.build());
    }

    public void updateNotification (String title, String text) throws Exception{
        if (builder==null){
            throw new Exception("Please call makeNotification first");
        }
        else {
            builder.setContentTitle(title);
            builder.setContentText(text);
            notificationManager.notify(0,builder.build());
        }
    }

    public void cancelNotification(){
        notificationManager.cancel(0);
    }


}
