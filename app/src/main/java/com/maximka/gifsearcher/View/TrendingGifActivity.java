package com.maximka.gifsearcher.View;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.maximka.gifsearcher.GiphyApp;
import com.maximka.gifsearcher.Model.GifPresentationModel;
import com.maximka.gifsearcher.Presenter.TrendingGifPresenter;
import com.maximka.gifsearcher.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrendingGifActivity extends BaseActivity<TrendingGifPresenter> implements GifView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData(false);
        getSupportActionBar().setTitle(R.string.trending_gif_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trending_gif_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                searchView.setIconified(true);
                Intent intent = new Intent(TrendingGifActivity.this, SearchGifActivity.class);
                intent.setAction(Intent.ACTION_SEARCH);
                intent.putExtra(SearchManager.QUERY, query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                return true;
            }
        });

        return true;
    }

    @NonNull
    @Override
    public TrendingGifPresenter createPresenter() {
        return GiphyApp.getInjector().getPresenter();
    }

    @Override
    public void loadData(boolean loadMore) {
        presenter.loadNewGif(loadMore);
    }
}
