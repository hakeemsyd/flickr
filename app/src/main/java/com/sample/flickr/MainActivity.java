package com.sample.flickr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.search_text)
    EditText mSearch;
    private String mSearchText = "";

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private List<Subscription> mSubs;

    private final TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //NOP
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!mSearchText.equals(s.toString())){
                mSearchText = s.toString();
                MainActivity.this.search(mSearchText);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSearch.addTextChangedListener(mWatcher);
    }

    @Override
    protected void onResume() {
        mSubs = new ArrayList<>();
        super.onResume();
    }

    @Override
    protected void onPause() {
        for (Subscription sub : mSubs) {
            if (!sub.isUnsubscribed()) {
                sub.unsubscribe();
            }
        }
        super.onPause();
    }

    private void search(String text){
        Log.i("", "Search: " + text);
        Subscription sub = FlickrClient.get().search(text)
         .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("", e.getMessage());
                    }

                    @Override
                    public void onNext(SearchResult photos) {
                        for(Photo p : photos.mList){
                            Log.i("", "Photo: " + p.mTitle );
                        }
                    }
                });


        mSubs.add(sub);

    }
}
