package com.sample.flickr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hakeem on 6/6/17.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mView;
        private Photo mItem;
        public ViewHolder(TextView view){
            super(view);
            mView = view;

        }

        public void update(Photo p){
            mItem = p;
            mView.setText(p.mTitle);
        }
    }

    private List<Photo> mItems;
    private Context mContext;
    public PhotosAdapter(Context context, List<Photo> items){
        mItems = items;
        mContext = context;
    }

    public void update(List<Photo> items){
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.img_view, parent, false);
        return new ViewHolder((TextView)view);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(mItems.get(position));
    }
}
