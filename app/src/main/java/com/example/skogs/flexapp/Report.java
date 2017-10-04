package com.example.skogs.flexapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by skogs on 2017-08-09.
 */
public class Report extends Fragment {
    private int mYear,mMonth,mDay;
    private Button dateButton;
    private Button workTimeButton;
    private Button inTimeButton;
    private Button outTimeButton;
    private Button lunchOutTimeButton;
    private Button lunchInTimeButton;
    private CheckBox sickness;

    private Button saveTime;
    private MainActivity mA;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report, container, false);
        mA = (MainActivity)getActivity();

        dateButton = (Button)view.findViewById(R.id.dateButton);
        workTimeButton = (Button)view.findViewById(R.id.workTimeButton);
        inTimeButton = (Button)view.findViewById(R.id.inButton);
        outTimeButton = (Button)view.findViewById(R.id.outButton);
        lunchOutTimeButton = (Button)view.findViewById(R.id.outLunchButton);
        lunchInTimeButton = (Button)view.findViewById(R.id.inLunchButton);
        sickness = (CheckBox)view.findViewById(R.id.sickBox);
        saveTime = (Button)view.findViewById(R.id.saveButton);

        if(mA.fH.unsavedDataFileExists(this.getContext())){
            String[] savedData = mA.fH.getSavedArray(this.getContext());
            dateButton.setText(savedData[getArrayNumber(dateButton.getId())]);
            workTimeButton.setText(savedData[getArrayNumber(workTimeButton.getId())]);
            inTimeButton.setText(savedData[getArrayNumber(inTimeButton.getId())]);
            outTimeButton.setText(savedData[getArrayNumber(outTimeButton.getId())]);
            lunchOutTimeButton.setText(savedData[getArrayNumber(lunchOutTimeButton.getId())]);
            lunchInTimeButton.setText(savedData[getArrayNumber(lunchInTimeButton.getId())]);
            sickness.setChecked(getChecked(savedData[getArrayNumber(sickness.getId())]));
        } else {
            dateButton.setText(getDate());
        }

        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                setDate();
            }
        });
        workTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                changeTime(v.getId(), 8, 0);
            }
        });
        inTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                changeCurrantTime(v.getId());
            }
        });
        outTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                changeCurrantTime(v.getId());
            }
        });
        lunchOutTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                changeCurrantTime(v.getId());
            }
        });
        lunchInTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                changeCurrantTime(v.getId());
            }
        });
        sickness.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                saveData();
            }
        });
        saveTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                saveReportedTime();
            }
        });

        return view;
    }

    private void setDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR); //sets startvalue
        mMonth = c.get(Calendar.MONTH); //sets startvalue
        mDay = c.get(Calendar.DAY_OF_MONTH); //sets startvalue

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String m = month + 1 + "";
                String d = day + "";
                if(month < 10){
                    m = "0" + m;
                }
                if(day < 10){
                    d = "0" + d;
                }
                setNewText(R.id.dateButton, year + " " + m + " " + d);
            }
        },mYear,mMonth,mDay);
        dpd.show();
    }

    private void changeCurrantTime(final int buttonId){
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("CET"));
        int mHour = c.get(Calendar.HOUR_OF_DAY); //sätter startvärden
        int mMinute = c.get(Calendar.MINUTE); //sätter startvärden
        changeTime(buttonId, mHour, mMinute);
    }

    private void changeTime(final int buttonId, int mHour, int mMinute){
        TimePickerDialog tpd = new TimePickerDialog(getActivity(), 3, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String h = hour + "";
                String m = minute + "";
                if(hour < 10){
                    h = "0" + h;
                }
                if(minute < 10){
                    m = "0" + m;
                }
                setNewText(buttonId, h + ":" + m);
            }
        }, mHour,mMinute,true);
        tpd.show();
    }

    private void setNewText(int buttonId, String time){
        switch (buttonId){
            case R.id.dateButton:
                dateButton.setText(time);
                break;
            case R.id.workTimeButton:
                workTimeButton.setText(time);
                break;
            case R.id.inButton:
                inTimeButton.setText(time);
                break;
            case R.id.outButton:
                outTimeButton.setText(time);
                break;
            case R.id.outLunchButton:
                lunchOutTimeButton.setText(time);
                break;
            case R.id.inLunchButton:
                lunchInTimeButton.setText(time);
                break;
        }
        saveData();
    }

    private void saveData(){
        String[] dataArray = new String[7];
        dataArray[getArrayNumber(dateButton.getId())] = dateButton.getText().toString();
        dataArray[getArrayNumber(workTimeButton.getId())] = workTimeButton.getText().toString();
        dataArray[getArrayNumber(inTimeButton.getId())] = inTimeButton.getText().toString();
        dataArray[getArrayNumber(outTimeButton.getId())] = outTimeButton.getText().toString();
        dataArray[getArrayNumber(lunchOutTimeButton.getId())] = lunchOutTimeButton.getText().toString();
        dataArray[getArrayNumber(lunchInTimeButton.getId())] = lunchInTimeButton.getText().toString();
        dataArray[getArrayNumber(sickness.getId())] = getCheckedText(sickness.isChecked());
        mA.fH.saveUnsavedData(dataArray, this.getContext());
    }

    private int getArrayNumber(int buttonId){
        int number = -1;
        switch (buttonId){
            case R.id.dateButton:
                number = 0;
                break;
            case R.id.workTimeButton:
                number = 1;
                break;
            case R.id.inButton:
                number = 2;
                break;
            case R.id.outButton:
                number = 3;
                break;
            case R.id.outLunchButton:
                number = 4;
                break;
            case R.id.inLunchButton:
                number = 5;
                break;
            case R.id.sickBox:
                number = 6;
                break;
        }
        return number;
    }

    private String getDate(){
        DateFormat df = new SimpleDateFormat("yyyy MM dd");
        return df.format(Calendar.getInstance().getTime());
    }

    private boolean getChecked(String checkedState){
        if(checkedState.equalsIgnoreCase("true")){
            return true;
        } else {
            return false;
        }
    }

    private String getCheckedText(boolean checkedState){
        if(checkedState){
            return "true";
        } else {
            return "false";
        }
    }

    private void saveReportedTime(){
        boolean registrationIsCorrect = true;
        registrationIsCorrect = saveCheck();

        if(registrationIsCorrect){
            //annars räkna ut dagsflex och spara till db och flex fil.
            //om något skrivs över måste gamla flexen hämtas upp och dras från flexfilen först.
            resetButtons();
            mA.fH.deleteUnsavedDataFile(this.getContext());
        }
    }

    private boolean saveCheck(){
        boolean registrationIsCorrect = true;
        int saveCheck = checkSave();
        if((saveCheck & 32) == 0){//check if sick
            if((saveCheck & 16) == 0){//check if worktime is registered dyker inte upp
                registrationIsCorrect = false;
                createWarningDialog("Working time is not registered");
            } else if(((saveCheck & 8) == 0) || ((saveCheck & 4) == 0)){//check if intime outtime is registered dyker upp trots att den är registrerad
                registrationIsCorrect = false;
                createWarningDialog("In time or out time is not registered");
            } else if(((saveCheck & 3) < 3) && ((saveCheck & 3) > 0)){//check if lunchtimes are complete
                registrationIsCorrect = false;
                createWarningDialog("In lunch time or out lunch time is not registered");
            } else {
                registrationIsCorrect = checkRegisteredTime();
            }
        }
        return registrationIsCorrect;
    }

    private boolean checkRegisteredTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("kk:mm");
        boolean isCorrect = true;
        try{
            if(sdf.parse(inTimeButton.getText().toString()).compareTo(
                    sdf.parse(outTimeButton.getText().toString())) == 1){
                isCorrect = false;
                createWarningDialog("Out time can not be earlier than in time");
            } else if(sdf.parse(lunchOutTimeButton.getText().toString()).compareTo(//felska bara comaira med lunch om lunch är registrerad
                    sdf.parse(lunchInTimeButton.getText().toString())) == 1){
                isCorrect = false;
                createWarningDialog("Lunch in time can not be earlier than lunch out time");
            } else if(sdf.parse(inTimeButton.getText().toString()).compareTo(//felska bara comaira med lunch om lunch är registrerad
                    sdf.parse(lunchOutTimeButton.getText().toString())) == 1){
                isCorrect = false;
                createWarningDialog("Lunch out time can not be earlier than in time");
            } else if(sdf.parse(outTimeButton.getText().toString()).compareTo(//felska bara comaira med lunch om lunch är registrerad
                    sdf.parse(lunchInTimeButton.getText().toString())) == -1){
                isCorrect = false;
                createWarningDialog("Lunch in time can not be later than out time");
            }
        }catch (ParseException pe){
            isCorrect = false;
            createWarningDialog("Undefined error");
        }
        return isCorrect;
    }

    private void createWarningDialog(String errorText){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext(), R.style.MyAlertDialog);
        builder.setMessage(errorText);
        builder.setTitle(R.string.dialogmessage);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int checkSave(){
        int saveCheck = 0;
        if(sickness.isChecked()){
            saveCheck = saveCheck + 32;
        }
        if(!workTimeButton.getText().toString().equalsIgnoreCase(getString(R.string.reportWorkTime))){
            saveCheck = saveCheck + 16;
        }
        if(!inTimeButton.getText().toString().equalsIgnoreCase(getString(R.string.reportIn))){
            saveCheck = saveCheck + 8;
        }
        if(!outTimeButton.getText().toString().equalsIgnoreCase(getString(R.string.reportOut))){
            saveCheck = saveCheck + 4;
        }
        if(!lunchOutTimeButton.getText().toString().equalsIgnoreCase(getString(R.string.reportLunchOut))){
            saveCheck = saveCheck + 2;
        }
        if(!lunchInTimeButton.getText().toString().equalsIgnoreCase(getString(R.string.reportLunchIn))){
            saveCheck = saveCheck + 1;
        }
        return saveCheck;
    }

    private void resetButtons(){
        dateButton.setText(getDate());
        workTimeButton.setText(R.string.reportWorkTime);
        inTimeButton.setText(R.string.reportIn);
        outTimeButton.setText(R.string.reportOut);
        lunchOutTimeButton.setText(R.string.reportLunchOut);
        lunchInTimeButton.setText(R.string.reportLunchIn);
        sickness.setChecked(false);
    }
}