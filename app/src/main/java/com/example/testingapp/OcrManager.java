package com.example.testingapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class OcrManager {

    public static boolean didCopy = false;

    public static final String TESS_DATA = "/tessdata";

    private TessBaseAPI tessBaseAPI;

    public OcrManager(Context context){
        if(!OcrManager.didCopy) {
            this.prepareTessData(context);
            OcrManager.didCopy = true;
        }
    }

    public String getText(Bitmap bitmap, Context context){
        try{
            tessBaseAPI = new TessBaseAPI();
        }catch (Exception e){
            e.printStackTrace();
        }

        AssetManager assetManager = context.getAssets();
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

    //copy Assets to external storage
    private void prepareTessData(Context context){
        try{
            File dir = context.getExternalFilesDir(TESS_DATA);
            if(!dir.exists()){
                if (!dir.mkdir()) {
                    Toast.makeText(context, "The folder " + dir.getPath() + "was not created", Toast.LENGTH_SHORT).show();
                }
            }
            String fileList[] = context.getAssets().list("");
            for(String fileName : fileList){
                String pathToDataFile = dir + "/" + fileName;
                if(!(new File(pathToDataFile)).exists()){
                    InputStream in = context.getAssets().open(fileName);
                    OutputStream out = new FileOutputStream(pathToDataFile);
                    byte [] buff = new byte[1024];
                    int len ;
                    while(( len = in.read(buff)) > 0){
                        out.write(buff,0,len);
                    }
                    in.close();
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
