package com.sample.flickr;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;

/**
 * Created by Hakeem on 6/6/17.
 */

public class FlickrClient {
    private static FlickrClient mClient;
    private final FlickrService mService;

    private FlickrClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mService = retrofit.create(FlickrService.class);
    }

    public static FlickrClient get() {
        if (mClient == null) {
            mClient = new FlickrClient();
        }
        return mClient;
    }

    public Observable<SearchResult> search(String text) {
        return mService.search("flickr.photos.search", Constants.API_KEY, text);
    }
}
