package com.maximka.gifsearcher.DI;

import com.maximka.gifsearcher.Model.Api;
import com.maximka.gifsearcher.Presenter.SearchGifPresenter;
import com.maximka.gifsearcher.Presenter.TrendingGifPresenter;

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
    public static final String ZERO_TIME = "1970-01-01 00:00:00";

    @Provides
    @Singleton
    TrendingGifPresenter providePresenter(Api api) {
        return new TrendingGifPresenter(api);
    }

    @Provides
    @Singleton
    SearchGifPresenter provideSearchPresenter(Api api) {
        return new SearchGifPresenter(api);
    }

    @Provides
    @Singleton
    Date provideZeroDate() {
        try {
            return new SimpleDateFormat(NetworkModule.DATE_PATTERN).parse(ZERO_TIME);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
