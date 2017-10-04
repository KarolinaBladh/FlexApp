package com.example.skogs.flexapp;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by skogs on 2017-08-10.
 */
public class ChangeTimeDateFragment extends Fragment {
    private Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    private static String dateTimeString;
    private TextView textDate;
    private Button dateButton;
    private Button timeButton;
    private TextView textTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedState){
        return inflater.inflate(R.layout.change_timedate, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedState){
        String dateString = editDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        textDate = (TextView)view.findViewById(R.id.currantDate);
        textDate.setText(dateString);

        dateButton = (Button)view.findViewById(R.id.setdate);
        dateButton.setOnClickListener(new ChangeDate());

        String timeString = editTime(calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
        textTime = (TextView)view.findViewById(R.id.currantTime);
        textTime.setText(timeString);

        timeButton = (Button)view.findViewById(R.id.settime);
        timeButton.setOnClickListener(new ChangeTime());

        dateTimeString = dateString + " " + timeString;
    }

    public Date getTimeDate(){
        //get choosen date
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            date = sdf.parse(dateTimeString);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    private String editTime(int hourOfDay, int minute){
        //create string from time
        String stringSecond = String.valueOf(calendar.get(Calendar.SECOND));
        if(stringSecond.length() == 1){
            stringSecond = "0" + stringSecond;
        }
        String stringHour = String.valueOf(hourOfDay);
        if(stringHour.length() == 1){
            stringHour = "0" + stringHour;
        }
        String stringMinute = String.valueOf(minute);
        if(stringMinute.length() == 1){
            stringMinute = "0" + stringMinute;
        }

        return (stringHour + ":" + stringMinute + ":" + stringSecond);
    }

    private String editDate(int year, int month, int dayOfMonth){
        //create string from date
        month++;
        String stringMonth = String.valueOf(month);
        if(stringMonth.length() == 1){
            stringMonth = "0" + stringMonth;
        }
        String stringDay = String.valueOf(dayOfMonth);
        if(stringDay.length() == 1){
            stringDay = "0" + stringDay;
        }
        return (String.valueOf(year) + "-" + stringMonth + "-" + stringDay);
    }

    private class ChangeDate implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //open datepickerdialog
            DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String dateString = editDate(year, month, dayOfMonth);
                    textDate.setText(dateString);
                    dateTimeString = dateString + dateTimeString.substring(10);
                }
            }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dpd.show();
        }
    }

    private class ChangeTime implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //open timepicker dialog
            TimePickerDialog tpd = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String timeString = editTime(hourOfDay, minute);
                    textTime.setText(timeString);
                    dateTimeString = dateTimeString.substring(0,11) + timeString;
                }
            }, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), true);

            tpd.show();
        }
    }
}