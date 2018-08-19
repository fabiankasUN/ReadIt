package Model.services.Yandex.Yandex;


import android.os.AsyncTask;
import android.util.Log;

import Model.utils.Erros;
import retrofit2.Call;

public class Caller {


    private Request request;

    public void getTranslate(){
        new getTranslateAsync(this).execute();
    }


    public Request getRequest(){
        return request;
    }
    public void setRequest( Request request){
        this.request = request;
    }
    private class getTranslateAsync extends AsyncTask<Void, Void, Request> {

        private Caller caller;
        public getTranslateAsync(Caller caller){
            this.caller = caller;
        }
        @Override
        protected Request doInBackground( Void... org0 ) {
            Request request = null;
            try{
                YandexApi api = YandexApi.retrofit.create(YandexApi.class);
                Call<Request> call = api.getTranslate("trnsl.1.1.20171231T185616Z.48551767dcb1dcbb.c0fabf20d90358c6e6776799b97f65646650eb69"
                        ,"hello world","en-es","plain");
                request = call.execute().body();
            }catch ( Exception ex )
            {
                Log.d(Erros.MAP_PROCESSOR, ex.getMessage());
            }
            return request;
        }

        @Override
        protected void onPostExecute(Request request) {

            caller.setRequest(request);
        }
    }


}
