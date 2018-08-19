package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class DetailMeaningAdapter extends ArrayAdapter<DetailMeaning> {


    public DetailMeaningAdapter(Context context, int resource, ArrayList<DetailMeaning> detailMeaning){
        super(context,resource, detailMeaning);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final DetailMeaning meaning = getItem(position);
        View view = convertView;
        if( view == null )
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_detail_meaning,parent,false);

        TextView text_word = (TextView) view.findViewById(R.id.text_language);

        text_word.setText(meaning.getLanguage());

        TextView text_translation = (TextView) view.findViewById(R.id.text_meaning);
        text_translation.setText(meaning.getText());

        //ImageView image_view = (ImageView) view.findViewById(R.id.detail_sound);

        //image_view.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View view) {
                //tts.speak(meaning.getText(),TextToSpeech.QUEUE_ADD,null,"DEFAULT");
        //    }
        //});

        return view;
    }

}
