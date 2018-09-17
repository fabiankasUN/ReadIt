package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Model.Calls.WordHandler;
import Model.Firebase.Firebase;
import Model.entity.Database;
import Model.Firebase.Entities.Word;
import Model.services.Yandex.Glosbe.GlosbeApi;
import Model.services.Yandex.Glosbe.RequestGlosbe;
import Model.services.Yandex.Yandex.Request;
import Model.services.Yandex.Yandex.YandexApi;
import Model.utils.Erros;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeaningsActivity extends AppCompatActivity {


    private TextToSpeech tts;
    private ArrayList<Meaning> mMeanings;
    private MeaningAdapter mAdapter;


    private AppCompatButton mBtnAddWord;
    private AppCompatButton mBtnAddAll;
    private AppCompatButton mBtnSpeech;

    private EditText mTxtSearch;

    private YandexApi yandexApi;
    private GlosbeApi glosbeApi;

    private String spokenText;
    private boolean isSpoken;


    private final String KEY_YANDEX = "trnsl.1.1.20171231T185616Z.48551767dcb1dcbb.c0fabf20d90358c6e6776799b97f65646650eb69";
    private final String FORMAT = "plain";
    private final String LANGUAGE = "en-es";
    public static final String YANDEX = "YANDEX";

    private ArrayList<String> selectedWords;
    private Database db;
    int id_book = 1;

    public static final String ARRAY = "array";
    public static  final String PHRASE = "phrase";
    public static  final String EXAMPLE = "example";
    public static final String WORD = "WORD";


    private static final int SPEECH_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meanings);

        isSpoken = false;
        db = Database.getDB(getBaseContext());

        initTts();

        setMeaningsArrayList();

        createAdapter();

        sortAdapter();

        initSearch();

        addButtons();

        glosbeApi = GlosbeApi.retrofit.create(GlosbeApi.class);
        yandexApi = YandexApi.retrofit.create(YandexApi.class);

    }

    public void addButtons(){
        mBtnAddWord = (AppCompatButton) findViewById(R.id.btn_add_word);

        mBtnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMeanings.add(new Meaning(mTxtSearch.getText().toString(),"0",0));
                mAdapter.getFilter().filter(mTxtSearch.getText().toString());
            }
        });

        mBtnAddAll = (AppCompatButton) findViewById(R.id.btn_add_all_words);

        mBtnAddAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SaveWords().execute(selectedWords);
            }
        });

        mBtnSpeech = (AppCompatButton) findViewById(R.id.btn_speech);

        mBtnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySpeechRecognizer();
            }
        });

    }

    public void createAdapter(){
        mAdapter = new MeaningAdapter(this,0, mMeanings, tts);

        ListView view = (ListView) findViewById(R.id.list);
        view.setAdapter(mAdapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ItemClick(position);
            }
        });

    }

    public void initSearch(){
        mTxtSearch = (EditText) findViewById(R.id.text_search);

        mTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setMeaningsArrayList(){
        ArrayList<Word> list = null;
        Bundle bundle = getIntent().getExtras();
        if( bundle != null ) {
            list = (ArrayList<Word>) bundle.getSerializable("Array");
            selectedWords = (ArrayList<String>) bundle.getSerializable("List");
        }
        mMeanings = new ArrayList<Meaning>();

        for( int i = 0; i < list.size(); i++){
            mMeanings.add(new Meaning(list.get(i).getWord(),list.get(i).getAmount()+"", list.get(i).getAmount()));
        }
    }

    public void sortAdapter(){
        mAdapter.sort(new Comparator<Meaning>() {
            @Override
            public int compare(Meaning meaning, Meaning meaning2) {
                if(meaning.getAmount() > meaning2.getAmount())
                    return 1;
                if(meaning.getAmount() < meaning2.getAmount())
                    return -1;

                return meaning.getWord().compareTo(meaning2.getWord());
                //return meaning.getWord().compareTo(meaning2.getWord());
            }
        });

        mAdapter.notifyDataSetChanged();
    }

    private class SaveWords extends AsyncTask<ArrayList<String>, Integer, Integer> {
        private WordHandler wordHandler;

        public SaveWords(){
            wordHandler = new WordHandler(db);
        }
        protected Integer doInBackground(ArrayList<String>... words) {

//            int count = 0;
//            for( int i =0; i < words[0].size(); i++ ){
//                Word word = db.wordDao().getWordByValue( words[0].get(i));
//                if( word == null ){
//                    db.wordDao().insert(new Word(words[0].get(i),1,id_book));
//                }else{
//                    db.wordDao().update(new Word(word.getIdWord(),words[0].get(i),word.getAmount()+1,id_book));
//                }
//                count++;
//            }
//            Log.e(Erros.MAP_PROCESSOR,"Words added : " + count+"");
//
            SaveFirebase(words[0]);
            //Toast.makeText(getBaseContext(),"Words added",Toast.LENGTH_SHORT).show();
            return 0;
        }

        public void SaveFirebase(final ArrayList<String> words){


            Firebase.Words.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<Map<String, Word>> t = new GenericTypeIndicator<Map<String, Word>>() {};
                    Map<String, Word> map = dataSnapshot.getValue(t);
                    if(words!=null)
                        for (String current: words){
                            if(map !=null && map.containsKey(current)){
                                Firebase.Words.child(current).setValue(new Word(current,map.get(current).getAmount() + 1));
                            }else if(current.length() > 0){
                                Firebase.Words.child(current).setValue(new Word(current,1));
                            }
                        }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        protected void onProgressUpdate(Integer... progress) {
        }
        protected void onPostExecute(Integer result) {

        }
    }


    @Override
    public void onResume(){
        super.onResume();
        if(isSpoken)
            mTxtSearch.setText(spokenText);
        else
            mTxtSearch.setText("");

        isSpoken = false;
    }


    public void ItemClick(final int position ){

        final Meaning item = mAdapter.getItem(position);;


        Call<RequestGlosbe> call = glosbeApi.translate("en"
                ,"es",item.getWord(),"true","json", "true");
        call.enqueue(new Callback<RequestGlosbe>() {
            @Override
            public void onResponse(Call<RequestGlosbe> call, Response<RequestGlosbe> response) {
                final RequestGlosbe request = response.body();
                //tts.speak(request.getTranslate(),TextToSpeech.QUEUE_ADD,null,"DEFAULT");
                Call<Request> callYandex = yandexApi.getTranslate(KEY_YANDEX,item.getWord(),LANGUAGE,FORMAT);
                callYandex.enqueue(new Callback<Request>() {
                    @Override
                    public void onResponse(Call<Request> call, Response<Request> response) {
                        Request resYandex = response.body();

                        if( request!=null && request.getMeanings()!=null && request.getMeanings().size() > 0 ) {
                            Intent intent = new Intent(getBaseContext(), DetailMeaningActivity.class);
                            intent.putExtra(ARRAY, request.getMeanings().get(0).meanings());
                            intent.putExtra(PHRASE, request.getMeanings().get(0).getPhrase());
                            intent.putExtra(EXAMPLE, request.getExamples());
                            intent.putExtra(WORD, item.getWord());
                            if( resYandex.getText()!= null && resYandex.getText().length > 0)
                                intent.putExtra(YANDEX, resYandex.getText()[0]);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getBaseContext(),"No existen significados en glosbe",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), DetailMeaningActivity.class);
                            if( resYandex.getText()!= null && resYandex.getText().length > 0)
                                intent.putExtra(YANDEX, resYandex.getText()[0]);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<Request> call, Throwable t) {

                    }
                });


            }

            @Override
            public void onFailure(Call<RequestGlosbe> call, Throwable t) {

            }
        });
    }


    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            if( results.size() > 0 )
                spokenText = results.get(0);
            isSpoken = true;
            // Do something with spokenText
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void initTts(){
        TextToSpeech.OnInitListener listener =
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(final int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            Log.d("TTS", "Text to speech engine started successfully.");
                            tts.setLanguage(Locale.US);
                        } else {
                            Log.d("TTS", "Error starting the text to speech engine.");
                        }
                    }
                };

        tts = new TextToSpeech(this.getApplicationContext(), listener);

    }
}
