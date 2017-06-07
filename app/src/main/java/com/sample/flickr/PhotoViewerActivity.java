package com.sample.flickr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shabbas on 6/6/17.
 */

public class PhotoViewerActivity extends AppCompatActivity {


    private static final String KEY_URI = "image_uri";

    @BindView(R.id.img)
    public ImageView mImage;

    public static Intent Create(Context context, String uri) {
        Intent i = new Intent();
        i.setClass(context, PhotoViewerActivity.class);
        i.putExtra(KEY_URI, uri);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            String uri = savedInstanceState.getString(KEY_URI, null);
            if (uri != null) {
                Picasso.with(this).load(uri).into(mImage);
            }
        }
    }
}
