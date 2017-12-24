package com.google.android.gms.samples.vision.ocrreader.Processors;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.List;


public interface IWordProcessor {

    int LINES = 30;
    int COLUMNS = 15;


    List<String> getWords();
    void process(Detector.Detections<TextBlock> detections);
    void init();
}
