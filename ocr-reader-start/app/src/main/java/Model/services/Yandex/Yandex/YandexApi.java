package Model.services.Yandex.Yandex;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexApi {

    String URL = "https://translate.yandex.net/api/v1.5/tr.json/";


    @GET("translate")
    Call<Request> getTranslate(@Query("key") String key,
                               @Query("text") String text, @Query("lang") String lang, @Query("format") String format );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
