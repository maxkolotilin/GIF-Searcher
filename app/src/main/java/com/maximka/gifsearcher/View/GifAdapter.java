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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
        GifPresentationModel item = items.get(position);

        holder.slug.setText(item.getSlug());
        holder.wasTrended.setVisibility(item.isWasTrended() ? View.VISIBLE : View.GONE);

        holder.thumbRequest = Glide.with(context)
                .load(item.getUrlStill())
                .asBitmap()
                .transcode(new BitmapToGlideDrawableTranscoder(context), GlideDrawable.class)
                .override(400, 300)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter();
        holder.thumbRequest.into(holder.gif);
        holder.r = null;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.gifSlug) TextView slug;
        @BindView(R.id.wasTrended) TextView wasTrended;
        @BindView(R.id.gif) ImageView gif;
        @BindView(R.id.loadGifProgress) ProgressBar progressBar;
        BitmapRequestBuilder<String, GlideDrawable> thumbRequest;
        GlideDrawable r;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            if (r == null) {
                progressBar.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(items.get(getAdapterPosition()).getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .thumbnail(thumbRequest)
                        .override(400, 300)
                        .dontAnimate()
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                r = resource;
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(gif);
            } else {
                if (r.isRunning()) {
                    r.stop();
                } else {
                    r.start();
                }
            }
        }
    }
}
