package com.maximka.gifsearcher.Presenter;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.maximka.gifsearcher.Model.Api;
import com.maximka.gifsearcher.Model.GifPresentationModel;
import com.maximka.gifsearcher.View.GifView;

import java.util.List;

import rx.Observable;

/**
 * Created by maximka on 16.11.16.
 */

public class TrendingGifPresenter extends MvpNullObjectBasePresenter<GifView>
        implements GifPresenter.ApiCallback {
    public static final int LIMIT = 20;
    private Api mApi;
    private int mOffset = 0;
    private GifPresenter mPresenter;

    public TrendingGifPresenter(Api api) {
        mApi = api;
        mPresenter = new GifPresenter(this);
    }

    public void loadNewGif(boolean loadMore) {
        mPresenter.loadNewGif(getView(), loadMore);
    }

    @Override
    public Observable<List<GifPresentationModel>> getObservableRequest() {
        return mApi.getTrendingGif(mOffset, LIMIT);
    }

    @Override
    public void incrementOffset(int increment) {
        mOffset += increment;
    }
}
