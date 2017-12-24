package com.google.android.gms.samples.vision.ocrreader.Processors;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.List;

/**
 * Created by Fabian on 23/12/2017.
 */
public interface IWordProcessor {

    public static final int LINES = 30;
    public static final int COLUMNS = 15;


    public abstract List<String> getWords();
    public abstract void process(Detector.Detections<TextBlock> detections);
    public abstract void init();
}
