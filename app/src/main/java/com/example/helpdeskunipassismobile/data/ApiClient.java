package com.example.helpdeskunipassismobile.data;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

public class ApiClient {

    // Em dev (emulador): PC em http://localhost:5260 -> http://10.0.2.2:5260
    private static final String DEV_BASE_URL = "http://10.0.2.2:5260/";

    // Se quiser apontar para produção depois, troque aqui ou use BuildConfig:
    // private static final String PROD_BASE_URL = "https://api.seudominio.com/";

    private static volatile Retrofit retrofit;

    private static String baseUrl() {
        // Troque a lógica se quiser usar BuildConfig.DEBUG ou um BuildConfig.BASE_URL
        return DEV_BASE_URL;
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            synchronized (ApiClient.class) {
                if (retrofit == null) {
                    HttpLoggingInterceptor log = new HttpLoggingInterceptor();
                    // Log só em debug:
                    log.setLevel(HttpLoggingInterceptor.Level.BODY);

                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .addInterceptor(log)
                            .build();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static ApiService getService() {
        return getInstance().create(ApiService.class);
    }
}
