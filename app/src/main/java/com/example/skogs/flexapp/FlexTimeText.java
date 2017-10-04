package com.example.skogs.flexapp;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by skogs on 2017-08-24.
 */
public class FlexTimeText extends TextView{//ha fragment i stället?
    private int hour;
    private int minute;

    public FlexTimeText(Context context) {
        super(context);
        setText(hour + ":" + minute);
    }

    public void addFlex(int totalMinutes){
        int newHour = totalMinutes/60;
        int newMinute = totalMinutes%60;
        int addedMinutes = minute + newMinute;
        if(addedMinutes > 59){
            newHour++;
            newMinute = addedMinutes%60;
        }
        hour = hour + newHour;
        minute = minute + newMinute;
    }

    public void removeFlex(int totalMinutes){
        int newHour = totalMinutes/60;
        int newMinute = totalMinutes%60;
        newMinute = minute - newMinute;
        if(newMinute < 0){
            newHour++;
            newMinute = 60 + newMinute;
        }
        hour = hour - newHour;
        minute = newMinute;
    }
}
