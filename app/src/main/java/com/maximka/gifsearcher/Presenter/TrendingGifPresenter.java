package com.maximka.gifsearcher.Presenter;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.maximka.gifsearcher.Model.GifPresentationModel;
import com.maximka.gifsearcher.Model.GiphyApi;
import com.maximka.gifsearcher.View.GifView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by maximka on 16.11.16.
 */

public class TrendingGifPresenter extends MvpNullObjectBasePresenter<GifView>
        implements GifPresenter.ApiCall {
    public static final int LIMIT = 20;
    private GiphyApi mApi;
    private int mOffset = 0;
    private GifPresenter mPresenter;

    public TrendingGifPresenter(GiphyApi api) {
        mApi = api;
        mPresenter = new GifPresenter(this);
    }

    public void loadNewGif(boolean loadMore) {
        mPresenter.loadNewGif(getView(), loadMore);
    }

    @Override
    public Observable<List<GifPresentationModel>> getObservableCall() {
        return mApi.getTrendingGif(mOffset, LIMIT)
                .flatMap(giphyResponse -> Observable.from(giphyResponse.data))
                .map(GifPresentationModel::new)
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void incrementOffset(int increment) {
        mOffset += increment;
    }
}
