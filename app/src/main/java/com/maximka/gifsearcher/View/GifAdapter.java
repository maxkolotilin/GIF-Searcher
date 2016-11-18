package com.maximka.gifsearcher.View;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.maximka.gifsearcher.GiphyApp;
import com.maximka.gifsearcher.ImageLoader;
import com.maximka.gifsearcher.Model.GifPresentationModel;
import com.maximka.gifsearcher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maximka on 16.11.16.
 */

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.ViewHolder> {
    private List<GifPresentationModel> items = new ArrayList<>();
    private ImageLoader loader;
    private Activity context;

    public GifAdapter(Activity context) {
        this.context = context;
        loader = GiphyApp.getInjector().getImageLoader();
    }

    public void addItems(List<GifPresentationModel> newItems) {
        int addPosition = items.size();
        items.addAll(newItems);
        notifyItemRangeInserted(addPosition, newItems.size());
    }

    public void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public GifAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gif_item, parent, false);
        return new GifAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GifAdapter.ViewHolder holder, int position) {
        GifPresentationModel gifInfo = items.get(position);

        holder.slug.setText(gifInfo.getSlug());
        holder.wasTrended.setVisibility(gifInfo.isWasTrended() ? View.VISIBLE : View.GONE);
        holder.setGifViewFixedHeight(gifInfo);
        holder.gif = null;
        loader.loadThumbnail(context, gifInfo, holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            RequestListener<String, GlideDrawable> {
        static final int DEFAULT_MAX_HEIGHT = 3000;
        static final int DEFAULT_MIN_HEIGHT = 500;

        @BindView(R.id.gifSlug) TextView slug;
        @BindView(R.id.wasTrended) TextView wasTrended;
        @BindView(R.id.gif) ImageView gifView;
        @BindView(R.id.loadGifProgress) ProgressBar progressBar;
        private BitmapRequestBuilder<String, GlideDrawable> thumbRequest;
        private GlideDrawable gif;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            progressBar.setVisibility(View.GONE);
        }

        public BitmapRequestBuilder<String, GlideDrawable> getThumbRequest() {
            return thumbRequest;
        }

        public ImageView getGifView() {
            return gifView;
        }

        public void setThumbRequest(BitmapRequestBuilder<String, GlideDrawable> thumbRequest) {
            this.thumbRequest = thumbRequest;
        }

        public void setGifViewFixedHeight(GifPresentationModel gifInfo) {
            int newHeight = (int) (gifView.getWidth() / gifInfo.getAspectRatio());
            gifView.setMinimumHeight(newHeight == 0 ? DEFAULT_MIN_HEIGHT : newHeight);
            gifView.setMaxHeight(newHeight == 0 ? DEFAULT_MAX_HEIGHT : newHeight);
        }

        @Override
        public void onClick(View v) {
            if (gif == null) {
                progressBar.setVisibility(View.VISIBLE);
                loader.loadGif(context, items.get(getAdapterPosition()), this);
            } else {
                clickOnLoadedGif();
            }
        }

        private void clickOnLoadedGif() {
            if (gif.isRunning()) {
                gif.stop();
            } else {
                gif.start();
            }
        }

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
            gif = resource;
            return false;
        }
    }
}
