package Model.services.Yandex.WordsApi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;
import retrofit2.http.Url;

public interface WordsApi {

    public static final String URL = "https://wordsapiv1.p.rapidapi.com/";

    @Headers("x-rapidapi-key: XbLrELh7OXmshS9pCELE7p5bol9Ip1X4iIwjsnlsiUULPa9M7x")
    @GET("words/{word}")
    Call<RequestWordsApi> getWord(@Path(value = "word", encoded = true) String word);

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
