package com.maximka.gifsearcher.View;

import android.app.SearchManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.maximka.gifsearcher.GiphyApp;
import com.maximka.gifsearcher.Presenter.SearchGifPresenter;
import com.maximka.gifsearcher.R;

public class SearchGifActivity extends BaseActivity<SearchGifPresenter> implements GifView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setQuery();
        loadData(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_gif_menu, menu);
        checkRating(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        String rating = item.getTitle().toString();
        GiphyApp.getPreferences().setRating(rating);
        if (presenter.setRating(rating)) {
            mAdapter.clearItems();
        }

        return true;
    }

    @NonNull
    @Override
    public SearchGifPresenter createPresenter() {
        return GiphyApp.getInjector().getSearchGifPresenter();
    }

    @Override
    public void loadData(boolean loadMore) {
        presenter.loadNewGif(loadMore);
    }

    private void setQuery() {
        String query = getIntent().getStringExtra(SearchManager.QUERY);
        getSupportActionBar().setTitle(query);
        presenter.setQuery(query);
    }

    private void checkRating(Menu menu) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            String itemTitle = item.getTitle().toString();
            if (GiphyApp.getPreferences().getRating().equals(itemTitle)) {
                item.setChecked(true);
                break;
            }
        }
    }
}
