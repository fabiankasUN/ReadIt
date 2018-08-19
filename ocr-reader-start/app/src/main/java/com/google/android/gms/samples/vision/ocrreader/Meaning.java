package com.google.android.gms.samples.vision.ocrreader;

import android.support.annotation.NonNull;

import java.io.Serializable;


public class Meaning implements Serializable, Comparable<Meaning> {


    private String word;
    private String translation;
    private int amount;

    public Meaning( String word, String translation, int amount ){
        this.word = word;
        this.translation = translation;
        this.amount = amount;
    }

    public String getWord(){
        return word;
    }
    public String getTranslation(){
        return translation;
    }
    public int getAmount(){
        return amount;
    }

    @Override
    public int compareTo(@NonNull Meaning meaning) {
        return this.amount - meaning.amount;
    }
}
