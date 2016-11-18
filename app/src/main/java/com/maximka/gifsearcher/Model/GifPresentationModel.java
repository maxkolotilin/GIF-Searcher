package com.maximka.gifsearcher.Model;

import com.maximka.gifsearcher.GiphyApp;

import java.util.Date;

/**
 * Created by maximka on 16.11.16.
 */

public class GifPresentationModel {
    public static final String DEFAULT_SLUG = "No slug";
    private String mSlug;
    private String mUrl;
    private String mUrlStill;
    private boolean mWasTrended;
    private int mWidth;
    private int mHeight;

    public GifPresentationModel(GiphyResponse.Gif gif) {
        mSlug = extractSlug(gif.slug, gif.id);
        mWasTrended = setWasTrended(gif.trending_datetime);
        mUrl = gif.images.downsized_medium.url;
        mUrlStill = gif.images.original_still.url;
        mWidth = gif.images.downsized_medium.width;
        mHeight = gif.images.downsized_medium.height;
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

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public double getAspectRatio() {
        return (double) mWidth / mHeight;
    }

    private String extractSlug(String rawSlug, String id) {
        String result = rawSlug.replace(id, "").replace("-", " ").trim();
        if (result.isEmpty()) {
            return DEFAULT_SLUG;
        }

        return capitalize(result);
    }

    private String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private boolean setWasTrended(Date trendingDate) {
        return !trendingDate.equals(GiphyApp.getInjector().getZeroDate());
    }
}
