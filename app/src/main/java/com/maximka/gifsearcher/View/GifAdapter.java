package com.maximka.gifsearcher.View;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maximka.gifsearcher.GiphyApp;
import com.maximka.gifsearcher.GifLoader;
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
    private Activity context;

    public GifAdapter(Activity context) {
        this.context = context;
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
        holder.progressBar.setVisibility(View.GONE);
        holder.setGifViewFixedHeight(gifInfo);
        holder.setThumbnail();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        static final int DEFAULT_MAX_HEIGHT = 3000;
        static final int DEFAULT_MIN_HEIGHT = 500;

        @BindView(R.id.gifSlug) TextView slug;
        @BindView(R.id.wasTrended) TextView wasTrended;
        @BindView(R.id.gif) ImageView gifView;
        @BindView(R.id.loadGifProgress) ProgressBar progressBar;
        private GifLoader loader;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            loader = GiphyApp.getInjector().getGifLoader();
            itemView.setOnClickListener(this);
        }

        public void setGifViewFixedHeight(GifPresentationModel gifInfo) {
            int newHeight = (int) (gifView.getWidth() / gifInfo.getAspectRatio());
            gifView.setMinimumHeight(newHeight == 0 ? DEFAULT_MIN_HEIGHT : newHeight);
            gifView.setMaxHeight(newHeight == 0 ? DEFAULT_MAX_HEIGHT : newHeight);
        }

        public void setThumbnail() {
            loader.loadThumbnail(context, items.get(getAdapterPosition()), gifView);
        }

        @Override
        public void onClick(View v) {
            if (loader.isGifLoaded()) {
                loader.switchGifState();
            } else {
                loader.loadGif(context, items.get(getAdapterPosition()), gifView, progressBar);
            }
        }
    }
}
