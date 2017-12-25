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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.google.android.gms.samples.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.HashMap;
import java.util.List;

import Model.entity.Word;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class OcrGraphic extends GraphicOverlay.Graphic {

    private int mId;

    private static final int TEXT_COLOR = Color.WHITE;

    private OcrDetectorProcessor processor;
    private static Paint sRectPaint;
    private static Paint sTextPaint;
    private final TextBlock mText;
    private int colors[] = {
            Color.parseColor("#FFE5E8"),
            Color.parseColor("#FFE5E8")
            ,Color.parseColor("#FFCCD1")
            ,Color.parseColor("#FFB2BB")
            ,Color.parseColor("#FF99A4")
            ,Color.parseColor("#FF7F8E")
            ,Color.parseColor("#FF6677")
            ,Color.parseColor("#FF4C60")
            ,Color.parseColor("#FF334A")
            ,Color.parseColor("#FF1933")
            ,Color.parseColor("#FF001D")
    };

    OcrGraphic(GraphicOverlay overlay, TextBlock text, OcrDetectorProcessor processor) {
        super(overlay);
        this.processor = processor;
        mText = text;

        if (sRectPaint == null) {
            sRectPaint = new Paint();
            sRectPaint.setColor(TEXT_COLOR);
            sRectPaint.setStyle(Paint.Style.STROKE);
            sRectPaint.setStrokeWidth(3.0f);
        }

        if (sTextPaint == null) {
            sTextPaint = new Paint();
            sTextPaint.setColor(TEXT_COLOR);
            sTextPaint.setTextSize(34.0f);
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public TextBlock getTextBlock() {
        return mText;
    }

    /**
     * Checks whether a point is within the bounding box of this graphic.
     * The provided point should be relative to this graphic's containing overlay.
     * @param x An x parameter in the relative context of the canvas.
     * @param y A y parameter in the relative context of the canvas.
     * @return True if the provided point is contained within this graphic's bounding box.
     */
    public boolean contains(float x, float y) {
        // TODO: Check if this graphic's text contains this point.
        if (mText == null) {
            return false;
        }
        RectF rect = new RectF(mText.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        return (rect.left < x && rect.right > x && rect.top < y && rect.bottom > y);
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */

    public boolean show( String s ){
        return !processor.getMapWords().containsKey(s);
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO: Draw the text onto the canvas.
        if (mText == null) {
            return;
        }

        // Draws the bounding box around the TextBlock.
        RectF rect = new RectF(mText.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        canvas.drawRect(rect, sRectPaint);

        // Break the text into multiple lines and draw each one according to its own bounding box.
        List<? extends Text> textComponents = mText.getComponents();

        for( int i = 0; i < textComponents.size(); i++ ){
            Text paragraph = textComponents.get(i);
            for( int j = 0; j < paragraph.getComponents().size(); j++ ){
                Text line = paragraph.getComponents().get(j);
                String value = line.getValue().replaceAll("[^a-zA-Z ]","").toLowerCase();
                float left = translateX(line.getBoundingBox().left);
                float bottom = translateY(line.getBoundingBox().bottom);
                sTextPaint.setColor(Color.RED);
                //Log.d("graph", line.getValue());
                if(show(value))
                    canvas.drawText(value, left, bottom, sTextPaint);

            }
        }


    }
}
