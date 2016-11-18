package com.maximka.gifsearcher.DI;

import com.maximka.gifsearcher.GifLoader;
import com.maximka.gifsearcher.Presenter.SearchGifPresenter;
import com.maximka.gifsearcher.Presenter.TrendingGifPresenter;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by maximka on 15.11.16.
 */

@Component(modules = {AppModule.class, NetworkModule.class})
@Singleton
public interface GiphyAppComponent {
    Date getZeroDate();
    TrendingGifPresenter getTrendingGifPresenter();
    SearchGifPresenter getSearchGifPresenter();
    GifLoader getGifLoader();
}
