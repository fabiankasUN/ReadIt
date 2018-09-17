/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.ocrreader;


import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.google.android.gms.samples.vision.ocrreader.Processors.CountProcessor;
import com.google.android.gms.samples.vision.ocrreader.Processors.IWordProcessor;
import com.google.android.gms.samples.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Model.Firebase.Firebase;
import Model.entity.Database;
import Model.Firebase.Entities.Word;
/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 * TODO: Make this implement Detector.Processor<TextBlock> and add text to the GraphicOverlay
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {


    private IWordProcessor wordProcessor;

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private Database db;
    private List<Model.Firebase.Entities.Word> allWords;
    private HashMap<String,Integer> mapWords;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, Database db, AppCompatActivity ac) {
        this.db = db;
        mGraphicOverlay = ocrGraphicOverlay;
        wordProcessor = new CountProcessor();
        reloadWords();
    }

    public void reloadWords(){
        new LoadWords(this,db).execute();
    }

    public void reloadProcessor(){
        wordProcessor.init();
    }

    private class LoadWords extends AsyncTask<Void, Void, List<Word>> {

        private Database db;
        List<Word> words;
        OcrDetectorProcessor processor;
        public LoadWords(OcrDetectorProcessor processor, Database db){
            this.processor = processor;
            this.db = db;
        }
        @Override
        protected List<Word> doInBackground( Void... org0 ) {


            Firebase.Words.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List<Word> words = new ArrayList<>();
                    for (DataSnapshot child: dataSnapshot.getChildren())
                        words.add(child.getValue(Word.class));
                    processor.setAllWords(words);
                    HashMap<String,Integer> mapWords = new HashMap<>();
                    for( int i = 0; i < words.size(); i++){
                        mapWords.put(words.get(i).getWord(),i);
                    }
                    processor.setMap(mapWords);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return words;
        }
        @Override
        protected void onPostExecute(List<Word> words) {

        }
    }

    public void restart(){
        wordProcessor = new CountProcessor();
        reloadWords();
    }
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        grafic( detections);
        wordProcessor.process(detections);
    }

    public void grafic(Detector.Detections<TextBlock> detections){
        mGraphicOverlay.clear();

        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);

            if (item != null && item.getValue() != null) {
                //Log.d("Processor", "Text detected! " + item.getValue());
            }
            OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item, this);
            mGraphicOverlay.add(graphic);
        }
    }

    public void setAllWords(List<Model.Firebase.Entities.Word> words){
        allWords = words;
    }

    public void setMap( HashMap<String,Integer> map ) { mapWords = map; }

    public HashMap< String,Integer > getMapWords(){
        return mapWords;
    }

    public List<Word> getAllWords(){
        return allWords;
    }

    public List<String> getSelectedWords(){
        return wordProcessor.getWords();
    }


    // TODO:  Once this implements Detector.Processor<TextBlock>, implement the abstract methods.
}
