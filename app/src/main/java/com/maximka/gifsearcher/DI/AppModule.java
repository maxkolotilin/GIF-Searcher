package com.maximka.gifsearcher.DI;

import android.content.Context;

import com.maximka.gifsearcher.Model.GiphyApi;
import com.maximka.gifsearcher.Presenter.TrendingGifPresenter;
import com.maximka.gifsearcher.Presenter.SearchGifPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by maximka on 15.11.16.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    TrendingGifPresenter providePresenter(GiphyApi api) {
        return new TrendingGifPresenter(api);
    }

    @Provides
    @Singleton
    SearchGifPresenter provideSearchPresenter(GiphyApi api) {
        return new SearchGifPresenter(api);
    }

    @Provides
    @Singleton
    Date provideZeroDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1970-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
