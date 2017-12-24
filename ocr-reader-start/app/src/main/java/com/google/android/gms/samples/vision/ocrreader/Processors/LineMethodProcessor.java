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


public class LineMethodProcessor implements IWordProcessor {

    private HashMap<Integer,HashMap<String, Integer>> map;
    private List<TextBlock> detections;

    public LineMethodProcessor(){
        init();
    }


    public  List<String> getWords(){
        return null;
    }
    public  void process(Detector.Detections<TextBlock> detections){
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

        for( int i =0; i < this.detections.size(); i++ ){
            for( int j = 0; j < this.detections.get(i).getComponents().size(); j++){
                List<? extends Text> line = this.detections.get(i).getComponents().get(j).getComponents();
                for( int k = 0; k < line.size(); k++ ){

                }

            }
        }


    }

    public void init(){
        detections = new ArrayList<>();
        map = new HashMap<>();
        for( int i = 0; i <= LINES; i++ ){
            map.put(i,new HashMap<String, Integer>());
        }

    }

}
