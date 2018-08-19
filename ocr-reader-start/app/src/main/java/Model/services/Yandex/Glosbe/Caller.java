package Model.services.Yandex.Glosbe;


import android.os.AsyncTask;
import android.util.Log;

import Model.utils.Erros;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Caller {

    private RequestGlosbe request;
    private String phrase;

    public Caller( String phrase ){
        this.phrase = phrase;
    }

    public void translate(){
        new getTranslateAsync(this).execute();
    }


    public RequestGlosbe getRequest(){
        return request;
    }
    public void setRequest( RequestGlosbe request){
        this.request = request;
    }


    private class getTranslateAsync extends AsyncTask<Void, Void, RequestGlosbe> {

        private Caller caller;
        public getTranslateAsync(Caller caller){
            this.caller = caller;
        }
        @Override
        protected RequestGlosbe doInBackground(Void... org0 ) {
            RequestGlosbe request = null;
            try{
                GlosbeApi api = GlosbeApi.retrofit.create(GlosbeApi.class);

                Call<RequestGlosbe> call = api.translate("en"
                        ,"es",phrase,"true","json", "true");

                request = call.execute().body();

            }catch ( Exception ex )
            {
                Log.d(Erros.MAP_PROCESSOR, ex.getMessage());
            }
            return request;
        }

        @Override
        protected void onPostExecute(RequestGlosbe request) {

            caller.setRequest(request);
        }
    }


}
