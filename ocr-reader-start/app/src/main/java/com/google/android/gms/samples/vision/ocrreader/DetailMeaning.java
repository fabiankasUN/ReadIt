package com.google.android.gms.samples.vision.ocrreader;


public class DetailMeaning {


    private String language;
    private String text;

    public DetailMeaning(String language, String text){
        this.language = language;
        this.text = text;
    }

    public String getLanguage(){
        return language;
    }

    public String getText(){
        return text;
    }


}
