package com.example.weatherapptwo;

import android.graphics.Bitmap;

public class MainModel {
    Integer langlogo;
    String langName;

    public MainModel(Integer langlogo, String langName){
        this.langlogo=langlogo;
        this.langName=langName;
    }
    public Integer getLanglogo(){
        return langlogo;
    }
    public String getLangName(){
        return langName;
    }
}
