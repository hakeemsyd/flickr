package com.sample.flickr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hakeem on 6/6/17.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mView;

        public PhotoViewHolder(ImageView view) {
            super(view);
            mView = view;
        }

        public void update(Photo p) {
            String url = "https://farm" + p.mFarm + ".staticflickr.com/" + p.mServer + "/" + p.mId + "_" + p.mSecret + "_m.jpg";
            final String urlLarge = "https://farm" + p.mFarm + ".staticflickr.com/" + p.mServer + "/" + p.mId + "_" + p.mSecret + "_b.jpg";
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(PhotoViewerActivity.Create(mContext, urlLarge));
                }
            });
            Picasso.with(mContext).load(url).into(mView);
        }
    }

    private List<Photo> mItems;
    private Context mContext;

    public PhotosAdapter(Context context, List<Photo> items) {
        mItems = items;
        mContext = context;
    }

    public void update(List<Photo> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.img_view, parent, false);
        return new PhotoViewHolder((ImageView) view);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.update(mItems.get(position));
    }
}
