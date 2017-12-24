package com.google.android.gms.samples.vision.ocrreader.Processors;

import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import Model.entity.Word;
import Model.utils.Erros;

/**
 * Created by Fabian on 23/12/2017.
 */
public class MapProcessor implements IWordProcessor{

    public static final int MIN_UMBRAL = 3;
    private List<TextBlock> detections;
    private String map[][];
    private int sum[][];


    public MapProcessor(){
       init();
    }


    public List<String> getWords(){
        List<String> selectedWords = new ArrayList<String>();
        for( int i = 0; i < map.length; i++ ){
            for( int j = 0; j < map[0].length; j++ ){
                if( sum[i][j] >= MIN_UMBRAL ){
                    selectedWords.add(map[i][j]);
                }
            }
        }
        return selectedWords;
    }


    public void process(Detector.Detections<TextBlock> detections){
        this.detections.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();

        Log.d("proccess", "Init proccess box " + items.size());
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            this.detections.add(item);
        }
        Collections.sort(this.detections, new Comparator<TextBlock>() {
            @Override
            public int compare(TextBlock tl1, TextBlock tl2) {
                return tl1.getBoundingBox().top - tl2.getBoundingBox().top;
            }
        });

        int total = 0;
        int id = 0;
        for (int i = 0; i < this.detections.size(); ++i) {
            //Log.d("proccess", " Bounding box : "  + i + " "+ this.detections.get(i).getBoundingBox().toString() + " lines " + this.detections.get(i).getComponents().size());
            total+= this.detections.get(i).getComponents().size();
            for( int j = 0; j < this.detections.get(i).getComponents().size(); j++ ){
                //Log.d("proccess", " Bounding box : "  + i + " "+ this.detections.get(i).getComponents().get(j).getBoundingBox().toString());
                if( id < map.length )
                    for( int k = 0; k < this.detections.get(i).getComponents().get(j).getComponents().size(); k++ ){
                        Text block = this.detections.get(i).getComponents().get(j).getComponents().get(k);
                        String value = block.getValue().replaceAll("[^a-zA-Z ]","").toLowerCase();
                        if( k < map[id].length )
                            if(map[id][k].equals("") || (!map[id][k].equals(value) && sum[id][k] < MIN_UMBRAL) ){
                                map[id][k] = value;
                                sum[id][k] = 1;
                            }else if( map[id][k].equals(value) ){
                                map[id][k] = value;
                                sum[id][k]++;
                            }
                        //Log.d("proccess", " line "  + j + " word "+ + k + " pos " +  block.getBoundingBox().toString() + " word " + block.getValue());
                    }
                id++;
            }
        }

        //print();
    }

    public void print(){
        for( int i = 0; i < LINES; i++ ){
            String s = "";
            for( int j = 0; j < COLUMNS; j++ ){
                s+=sum[i][j] + " ";
            }
            Log.d(Erros.MAP_PROCESSOR,s);
        }

        for( int i = 0; i < LINES; i++ ){
            String s = "";
            for( int j = 0; j < COLUMNS; j++ ){
                s+=map[i][j] + " ";
            }
            Log.d(Erros.MAP_PROCESSOR,s);
        }

    }

    public void init(){
        detections = new ArrayList<>();
        map = new String[LINES][COLUMNS];
        sum = new int[LINES][COLUMNS];

        for( int i = 0; i < LINES; i++ ){
            map[i] = new String[COLUMNS];
            sum[i] = new int[COLUMNS];
            for( int j = 0; j  < COLUMNS; j++ ){
                map[i][j] = "";
                sum[i][j] = 0;
            }
        }
    }

}
