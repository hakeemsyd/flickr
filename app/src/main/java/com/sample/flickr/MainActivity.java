package com.sample.flickr;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_SEARCH_QUERY = "seary_query_key";
    @BindView(R.id.search_text)
    EditText mSearch;
    private String mSearchText = "";

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private PhotosAdapter mAdapter;

    private List<Subscription> mSubs;
    private int mPage;

    private PublishSubject<String> mSubject;
    private LinearLayoutManager mLayoutManager;

    private final TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //NOP
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!mSearchText.equals(s.toString())) {
                mSearchText = s.toString();
                mSubject.onNext(s.toString());
            }
        }
    };

    private final Action1<String> mSearchAction = new Action1<String>() {
        @Override
        public void call(String s) {
            MainActivity.this.search(s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new PhotosAdapter(this, Collections.<Photo>emptyList());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSearch.addTextChangedListener(mWatcher);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(mAdapter);
        /*mRecycler.setOnScrollListener(new RecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.i("", "Pagination: " + current_page);
            }
        });*/

        mSubject = PublishSubject.create();
        mSubject.debounce(20, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .subscribe(mSearchAction);

        if(savedInstanceState != null){
            String q = savedInstanceState.getString(KEY_SEARCH_QUERY, null);
            if(q !=null){
                search(q);
            }
        }

    }

    @Override
    protected void onResume() {
        mSubs = new ArrayList<>();
        mPage = 0;
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(KEY_SEARCH_QUERY, mSearchText);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void search(String text) {
        Log.i("", "Search: " + text);
        Subscription sub = FlickrClient.get().search(text, mPage)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(SearchResult photos) {
                        Log.i("", "SearchResult: " + mSearchText);
                        if (photos.mList == null) {
                            mAdapter.update(Collections.<Photo>emptyList());
                            mPage = 0;
                        } else {
                            mPage = Integer.valueOf(photos.mPage);
                            mAdapter.update(photos.mList);
                        }

                    }
                });


        mSubs.add(sub);

    }
}
