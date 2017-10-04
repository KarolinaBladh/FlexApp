package com.example.skogs.flexapp;

import android.content.Context;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by skogs on 2017-08-20.
 */
public class FileHandler {
    private static String UNSAVED_DATA = "unsaved_data";
    private static String FLEX_DATA = "flex_data";

    protected boolean saveUnsavedData(String[] timeStrings, Context context){
        String newString = timeStrings[0];
        for(int i = 1; i < timeStrings.length; i++){
            newString = newString + "," + timeStrings[i];
        }
        return writeToFile(newString, UNSAVED_DATA,context);
    }

    protected boolean saveFlexData(Context context){//not done
        return true;
    }

    protected String findData(int arrayNumber, Context context){
        String [] savedTimes = getSavedArray(context);
        return savedTimes[arrayNumber];
    }

    protected String getFlexData(Context context){
        if(fileExists(FLEX_DATA, context)){
            return readFromFile(context, FLEX_DATA);
        }else{
            return "00:00";
        }
    }

    protected boolean unsavedDataFileExists(Context context){
        return fileExists(UNSAVED_DATA, context);
    }

    private boolean fileExists(String fileName, Context context){
        File tempFile = new File(context.getFilesDir().getAbsolutePath(), fileName);
        return tempFile.exists();
    }

    protected void deleteUnsavedDataFile(Context context){
        context.deleteFile(UNSAVED_DATA);
    }
    protected void deleteFlexDataFile(Context context){context.deleteFile(FLEX_DATA);}

    private boolean writeToFile(String timeString, String fileName, Context context){
        boolean writtenToFile;
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(timeString.getBytes());
            fos.close();
            writtenToFile = true;
        }catch (IOException e){
            writtenToFile = false;
        }

        return writtenToFile;
    }

    protected String[] getSavedArray(Context context){
        String savedTimes = readFromFile(context, UNSAVED_DATA);
        if(savedTimes.equalsIgnoreCase("")){
            return new String[7];
        } else {
            return savedTimes.split(",");
        }
    }

    private String readFromFile(Context context, String fileName){
        String fileLine = "";
        try{
            FileInputStream fis = context.openFileInput(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            fileLine = br.readLine();
            br.close();
            fis.close();
        }catch (IOException e){

        }
        return fileLine;
    }
}
