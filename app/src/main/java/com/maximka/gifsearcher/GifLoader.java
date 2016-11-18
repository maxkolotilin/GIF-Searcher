package com.maximka.gifsearcher;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.maximka.gifsearcher.Model.GifPresentationModel;

/**
 * Created by maximka on 18.11.16.
 */

public class GifLoader {
    private BitmapRequestBuilder<String, GlideDrawable> mThumbnailRequest;
    private GlideDrawable mGif;

    public void loadThumbnail(Activity context, GifPresentationModel gifInfo,
                              ImageView view) {
        mThumbnailRequest = Glide.with(context)
                .load(gifInfo.getUrlStill())
                .asBitmap()
                .transcode(new BitmapToGlideDrawableTranscoder(context), GlideDrawable.class)
                .override(gifInfo.getWidth(), gifInfo.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        mThumbnailRequest.into(view);
        mGif = null;
    }

    public boolean isGifLoaded() {
        return mGif != null;
    }

    public void switchGifState() {
        if (mGif.isRunning()) {
            mGif.stop();
        } else {
            mGif.start();
        }
    }

    public void loadGif(Activity context, GifPresentationModel gifInfo,
                        ImageView view, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(gifInfo.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(mThumbnailRequest)
                .override(gifInfo.getWidth(), gifInfo.getHeight())
                .dontAnimate()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        mGif = resource;
                        return false;
                    }
                })
                .into(view);
    }
}
