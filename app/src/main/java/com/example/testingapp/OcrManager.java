package com.example.testingapp;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

public class OcrManager {

    private TessBaseAPI tessBaseAPI;

    private String getText(Bitmap bitmap, Context context){
        try{
            tessBaseAPI = new TessBaseAPI();
        }catch (Exception e){
            e.printStackTrace();
        }

        String dataPath = context.getExternalFilesDir("/").getPath() + "/";

        tessBaseAPI.init(dataPath, "heb");
        tessBaseAPI.setImage(bitmap);
        String retStr = "No result";
        try{
            retStr = tessBaseAPI.getUTF8Text();
        }catch (Exception e){
            e.printStackTrace();
        }
        tessBaseAPI.end();
        return retStr;
    }






}
