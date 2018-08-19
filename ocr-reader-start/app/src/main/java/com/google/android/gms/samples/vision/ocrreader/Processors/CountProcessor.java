package com.google.android.gms.samples.vision.ocrreader.Processors;


import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountProcessor implements IWordProcessor{
    private HashMap<String,Integer> map;
    private List<TextBlock> detections;

    private final int MIN_UMBRAL = 3;

    public CountProcessor(){
        init();
    }


    public  List<String> getWords(){
        List<String> selectedWords = new ArrayList<String>();

        for(Map.Entry<String,Integer> entry: map.entrySet()){
            if(entry.getValue() >= MIN_UMBRAL ){
                    selectedWords.add(entry.getKey());
            }
        }
        return selectedWords;
    }


    public  void process(Detector.Detections<TextBlock> detections){
        this.detections.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();

        //Log.d("proccess", "Init proccess box " + items.size());
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
        int id = 0;
        for( int i =0; i < this.detections.size(); i++ ){
            for( int j = 0; j < this.detections.get(i).getComponents().size(); j++){
                if(id < LINES){
                    List<? extends Text> line = this.detections.get(i).getComponents().get(j).getComponents();
                    for( int k = 0; k < line.size(); k++ ){
                        Text block = this.detections.get(i).getComponents().get(j).getComponents().get(k);
                        String value = block.getValue().replaceAll("[^a-zA-Z ]","").toLowerCase();
                            if( map.containsKey(value)){
                                map.put(value,map.get(value)+1);
                            }else{
                                map.put(value,1);
                            }
                    }
                }
                id++;
            }
        }


    }

    public void init(){
        detections = new ArrayList<>();
        map = new HashMap<>();
    }


}
