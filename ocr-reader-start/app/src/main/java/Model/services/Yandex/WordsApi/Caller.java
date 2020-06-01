package Model.services.Yandex.WordsApi;


import android.os.AsyncTask;
import android.util.Log;

import Model.utils.Erros;
import retrofit2.Call;

public class Caller {

    private RequestWordsApi request;
    private String word;

    public Caller( String word ){
        this.word = word;
    }

    public void translate(){
        new getTranslateAsync(this).execute();
    }


    public RequestWordsApi getRequest(){
        return request;
    }
    public void setRequest( RequestWordsApi request){
        this.request = request;
    }


    private class getTranslateAsync extends AsyncTask<Void, Void, RequestWordsApi> {

        private Caller caller;
        public getTranslateAsync(Caller caller){
            this.caller = caller;
        }
        @Override
        protected RequestWordsApi doInBackground(Void... org0 ) {
            RequestWordsApi request = null;
            try{
                WordsApi api = WordsApi.retrofit.create(WordsApi.class);

                Call<RequestWordsApi> call = api.getWord(word);

                request = call.execute().body();

            }catch ( Exception ex )
            {
                Log.d(Erros.MAP_PROCESSOR, ex.getMessage());
            }
            return request;
        }

        @Override
        protected void onPostExecute(RequestWordsApi request) {

            caller.setRequest(request);
        }
    }


}
