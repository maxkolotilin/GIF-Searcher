package com.maximka.gifsearcher.View;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.maximka.gifsearcher.Model.GifPresentationModel;
import com.maximka.gifsearcher.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maximka on 17.11.16.
 */

public abstract class BaseActivity<P extends MvpPresenter<GifView>> extends
        MvpLceActivity<SuperRecyclerView, List<GifPresentationModel>, GifView, P> {
    public static final int BEFORE_MORE_THRESHOLD = 1;
    @BindView(R.id.contentView) SuperRecyclerView mGifList;
    @BindView(R.id.toolBar) Toolbar mBar;
    protected GifAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_gif);
        ButterKnife.bind(this);
        setSupportActionBar(mBar);
        prepareGifList();
    }

    @Override
    public void setData(List<GifPresentationModel> data) {
        mAdapter.addItems(data);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean loadMore) {
        return getResources().getString(R.string.error_message);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        mGifList.setLoadingMore(false);
        mGifList.hideMoreProgress();
        super.showError(e, pullToRefresh);
    }

    protected void prepareGifList() {
        mAdapter = new GifAdapter(this);
        mGifList.setLayoutManager(new LinearLayoutManager(this));
        mGifList.setAdapter(mAdapter);
        mGifList.setupMoreListener((overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) ->
                loadData(true), BEFORE_MORE_THRESHOLD);
    }
}
