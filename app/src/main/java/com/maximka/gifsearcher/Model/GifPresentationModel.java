package com.maximka.gifsearcher.Model;

import com.maximka.gifsearcher.GiphyApp;

import java.util.Date;

/**
 * Created by maximka on 16.11.16.
 */

public class GifPresentationModel {
    private String mSlug;
    private String mUrl;
    private String mUrlStill;
    private boolean mWasTrended;

    public GifPresentationModel(GiphyResponse.Gif gif) {
        mSlug = extractSlug(gif.slug, gif.id);
        mWasTrended = setWasTrended(gif.trending_datetime);
        mUrl = gif.images.downsized.url;
        mUrlStill = gif.images.downsized_still.url;
    }

    public String getSlug() {
        return mSlug;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUrlStill() {
        return mUrlStill;
    }

    public boolean isWasTrended() {
        return mWasTrended;
    }

    private String extractSlug(String rawSlug, String id) {
        String result = rawSlug.replace(id, "").replace("-", " ").trim();
        if (result.isEmpty()) {
            return "No slug";
        }

        return capitalize(result);
    }

    private String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private boolean setWasTrended(Date trendingDate) {
        if (trendingDate.equals(GiphyApp.getInjector().getZeroDate())) {
            return false;
        }

        return true;
    }
}
