package com.maximka.gifsearcher.Presenter;

import com.maximka.gifsearcher.Model.GifPresentationModel;
import com.maximka.gifsearcher.View.GifView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by maximka on 17.11.16.
 */

public class GifPresenter {
    private ApiCallback mApiCallback;
    private List<GifPresentationModel> mLoadedGif = new ArrayList<>();
    private boolean mIsBusy = false;

    public GifPresenter(ApiCallback apiCallback) {
        mApiCallback = apiCallback;
    }

    public void loadNewGif(GifView view, boolean loadMore) {
        if (!mIsBusy) {
            if (restoreContent(view, loadMore)) {
                return;
            }
            mIsBusy = true;
            mApiCallback.getObservableRequest()
                    .subscribe(response -> onNext(view, response),
                            throwable -> onError(view, throwable, loadMore)
            );
        }
        view.showLoading(loadMore);
    }

    public void clear() {
        mLoadedGif.clear();
    }

    private void onNext(GifView view, List<GifPresentationModel> gifList) {
        view.setData(gifList);
        view.showContent();
        mLoadedGif.addAll(gifList);
        mApiCallback.incrementOffset(gifList.size());
        mIsBusy = false;
    }

    private void onError(GifView view, Throwable throwable, boolean loadMore) {
        view.showError(throwable, loadMore);
        mIsBusy = false;
    }

    private boolean restoreContent(GifView view, boolean loadMore) {
        if (!loadMore && !mLoadedGif.isEmpty()) {
            view.setData(mLoadedGif);
            view.showContent();
            return true;
        }

        return false;
    }

    public interface ApiCallback {
        Observable<List<GifPresentationModel>> getObservableRequest();
        void incrementOffset(int increment);
    }
}
