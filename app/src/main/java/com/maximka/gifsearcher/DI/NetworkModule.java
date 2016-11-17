package com.maximka.gifsearcher.DI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maximka.gifsearcher.Model.GiphyApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by maximka on 15.11.16.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public GiphyApi provideGiphyApi(Gson parser) {
        return new Retrofit.Builder()
                .baseUrl(GiphyApi.API_ROOT)
                .addConverterFactory(GsonConverterFactory.create(parser))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(GiphyApi.class);
    }

    @Provides
    public Gson provideJsonParser() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }
}
