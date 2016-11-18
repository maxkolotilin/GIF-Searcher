package com.maximka.gifsearcher;

import android.app.Activity;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;
import com.maximka.gifsearcher.Model.GifPresentationModel;
import com.maximka.gifsearcher.View.GifAdapter;

/**
 * Created by maximka on 18.11.16.
 */

public class ImageLoader {
    public void loadThumbnail(Activity context, GifPresentationModel gifInfo,
                              GifAdapter.ViewHolder holder) {
        BitmapRequestBuilder<String, GlideDrawable> thumbRequest = Glide.with(context)
                .load(gifInfo.getUrlStill())
                .asBitmap()
                .transcode(new BitmapToGlideDrawableTranscoder(context), GlideDrawable.class)
                .override(gifInfo.getWidth(), gifInfo.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        thumbRequest.into(holder.getGifView());
        holder.setThumbRequest(thumbRequest);
    }

    public void loadGif(Activity context, GifPresentationModel gifInfo,
                        GifAdapter.ViewHolder holder) {
        Glide.with(context)
                .load(gifInfo.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(holder.getThumbRequest())
                .override(gifInfo.getWidth(), gifInfo.getHeight())
                .dontAnimate()
                .listener(holder)
                .into(holder.getGifView());
    }
}
