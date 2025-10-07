package com.example.helpdeskunipassismobile.data;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

public class ApiClient {

    // Emulador -> host (sua máquina): 10.0.2.2
    // Se sua API estiver em HTTP (dev), use http://
    // Ajuste a PORTA para a sua (ex.: 5154/7154).
    private static final String BASE_URL = "http://10.0.2.2:7154/";

    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor log = new HttpLoggingInterceptor();
            log.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(log)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static ApiService getService() {
        return getInstance().create(ApiService.class);
    }
}
