package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;


public class MeaningAdapter extends ArrayAdapter<Meaning> implements Filterable{


    private TextToSpeech tts;

    private ArrayList<Meaning> mDisplayedValues;
    private ArrayList<Meaning> mOriginalValues;

    @Override
    public void notifyDataSetChanged() {
        //do your sorting here
        super.notifyDataSetChanged();
    }

    public MeaningAdapter(Context context, int resource, ArrayList<Meaning> meanings, TextToSpeech tts){
        super(context,resource, meanings);
        this.mOriginalValues = meanings;
        this.mDisplayedValues = meanings;
        this.tts = tts;
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Meaning getItem(int position) {
        return mDisplayedValues.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Meaning meaning = getItem(position);
        View view = convertView;
        if( view == null )
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_meaning,parent,false);

        TextView text_word = (TextView) view.findViewById(R.id.text_word);

        text_word.setText(mDisplayedValues.get(position).getWord());

        TextView text_translation = (TextView) view.findViewById(R.id.text_translation);
        text_translation.setText(mDisplayedValues.get(position).getTranslation());

        ImageView image_view = (ImageView) view.findViewById(R.id.image_view_sound);

        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(meaning.getWord(),TextToSpeech.QUEUE_ADD,null,"DEFAULT");
            }
        });
        return view;

    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<Meaning>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Meaning> FilteredArrList = new ArrayList<Meaning>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Meaning>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getWord();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Meaning(mOriginalValues.get(i).getWord(),mOriginalValues.get(i).getAmount()+"",0));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

}
