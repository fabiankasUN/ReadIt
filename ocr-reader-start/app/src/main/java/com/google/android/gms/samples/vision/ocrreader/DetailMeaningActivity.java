package com.google.android.gms.samples.vision.ocrreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;

import Model.entity.Word;
import Model.services.Yandex.Glosbe.RequestGlosbe;

public class DetailMeaningActivity extends AppCompatActivity {

    private ArrayList<DetailMeaning> mDetailMeanings;
    private DetailMeaningAdapter mAdapter;
    private String word;
    private String yandexTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_meaning);

        ArrayList<RequestGlosbe.Mean> listMeanings = null;
        Bundle bundle = getIntent().getExtras();
        if( bundle != null ){
            word = bundle.getString(MeaningsActivity.WORD);
            listMeanings = (ArrayList<RequestGlosbe.Mean>) bundle.getSerializable(MeaningsActivity.ARRAY);
            yandexTranslation = bundle.getString(MeaningsActivity.YANDEX);
        }
        setTitle(word);

        mDetailMeanings = new ArrayList<>();

        if( listMeanings!=null )
            for( int i = 0; i < listMeanings.size(); i++){
                mDetailMeanings.add(new DetailMeaning(listMeanings.get(i).getLanguage(),listMeanings.get(i).getText()));
            }
        RequestGlosbe.Phrase phrase = (RequestGlosbe.Phrase)bundle.get(MeaningsActivity.PHRASE);
        if(phrase!=null )
            mDetailMeanings.add(new DetailMeaning( phrase.getLanguage(),phrase.getText()));

        if( yandexTranslation!=null ){
            mDetailMeanings.add(new DetailMeaning("es", yandexTranslation));
        }


        mAdapter = new DetailMeaningAdapter(this,0,mDetailMeanings);
        ListView view = (ListView) findViewById(R.id.list_detail_meaning);
        view.setAdapter(mAdapter);

        mAdapter.sort(new Comparator<DetailMeaning>() {
            @Override
            public int compare(DetailMeaning meaning, DetailMeaning meaning2) {
                return meaning.getLanguage().compareTo(meaning2.getLanguage());
                //return meaning.getWord().compareTo(meaning2.getWord());
            }
        });

        mAdapter.notifyDataSetChanged();




    }
}
