package com.example.testingapp;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

public class OcrManager {

    private TessBaseAPI tessBaseAPI;

    private String getText(Bitmap bitmap, String dataPath){
        try{
            tessBaseAPI = new TessBaseAPI();
        }catch (Exception e){
            e.printStackTrace();
        }

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
