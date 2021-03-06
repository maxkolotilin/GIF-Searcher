package com.maximka.gifsearcher.DI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maximka.gifsearcher.GifLoader;
import com.maximka.gifsearcher.Model.Api;
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
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Provides
    @Singleton
    Api provideApi(GiphyApi api) {
        return new Api(api);
    }

    @Provides
    GiphyApi provideGiphyApi(Gson parser) {
        return new Retrofit.Builder()
                .baseUrl(GiphyApi.API_ROOT)
                .addConverterFactory(GsonConverterFactory.create(parser))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(GiphyApi.class);
    }

    @Provides
    Gson provideJsonParser() {
        return new GsonBuilder()
                .setDateFormat(DATE_PATTERN)
                .create();
    }

    @Provides
    GifLoader provideImageLoader() {
        return new GifLoader();
    }
}
