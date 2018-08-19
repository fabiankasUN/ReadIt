package Model.services.Yandex.Glosbe;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GlosbeApi {

    String URL = "https://glosbe.com/gapi/";


    @GET("translate")
    Call<RequestGlosbe> translate(@Query("from") String from,
                                     @Query("dest") String dest, @Query("phrase") String phrase, @Query("tm") String tm,
                                  @Query("format") String format , @Query("pretty") String pretty);

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();;
}
