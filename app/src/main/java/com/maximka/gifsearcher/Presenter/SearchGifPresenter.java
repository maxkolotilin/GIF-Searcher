package com.maximka.gifsearcher.Presenter;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.maximka.gifsearcher.GiphyApp;
import com.maximka.gifsearcher.Model.Api;
import com.maximka.gifsearcher.Model.GifPresentationModel;
import com.maximka.gifsearcher.Model.GiphyApi;
import com.maximka.gifsearcher.View.GifView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by maximka on 17.11.16.
 */

public class SearchGifPresenter extends MvpNullObjectBasePresenter<GifView>
        implements GifPresenter.ApiCall {
    public static final int LIMIT = 20;
    private Api mApi;
    private GifPresenter mPresenter;
    private int mOffset = 0;
    private String mRating = GiphyApp.getPreferences().getRating();
    private String mQuery;

    public SearchGifPresenter(Api api) {
        mApi = api;
        mPresenter = new GifPresenter(this);
    }

    public boolean setRating(String rating) {
        if (!rating.equals(mRating)) {
            mRating = rating;
            reset();
            return true;
        }

        return false;
    }

    public void setQuery(String query) {
        if (!query.equals(mQuery)) {
            mQuery = query;
            reset();
        }
    }

    private void reset() {
        mOffset = 0;
        mPresenter.clear();
        loadNewGif(false);
    }

    public void loadNewGif(boolean loadMore) {
        mPresenter.loadNewGif(getView(), loadMore);
    }

    @Override
    public Observable<List<GifPresentationModel>> getObservableRequest() {
        return mApi.searchGif(mQuery, mRating, mOffset, LIMIT);
    }

    @Override
    public void incrementOffset(int increment) {
        mOffset += increment;
    }
}
