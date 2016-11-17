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
    private ApiCall mApiCall;
    private List<GifPresentationModel> mLoadedGif = new ArrayList<>();
    private boolean mIsBusy = false;

    public GifPresenter(ApiCall apiCall) {
        mApiCall = apiCall;
    }

    public void loadNewGif(GifView view, boolean loadMore) {
        if (!mIsBusy) {
            if (restoreContent(view, loadMore)) {
                return;
            }
            mIsBusy = true;
            mApiCall.getObservableCall()
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
        mLoadedGif.addAll(gifList);
        view.showContent();
        mApiCall.incrementOffset(gifList.size());
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

    public interface ApiCall {
        Observable<List<GifPresentationModel>> getObservableCall();
        void incrementOffset(int increment);
    }
}
