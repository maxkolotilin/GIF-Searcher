package com.maximka.gifsearcher.Model;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by maximka on 17.11.16.
 */

public class Api {
    GiphyApi mGiphyApi;

    public Api(GiphyApi mGiphyApi) {
        this.mGiphyApi = mGiphyApi;
    }

    public Observable<List<GifPresentationModel>> getTrendingGif(int offset, int limit) {
        return mGiphyApi.getTrendingGif(offset, limit)
                .flatMap(giphyResponse -> Observable.from(giphyResponse.data))
                .map(GifPresentationModel::new)
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<GifPresentationModel>> searchGif(String query, String rating,
                                                            int offset, int limit) {
        return mGiphyApi.searchGif(query, offset, limit, rating)
                .flatMap(giphyResponse -> Observable.from(giphyResponse.data))
                .map(GifPresentationModel::new)
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
