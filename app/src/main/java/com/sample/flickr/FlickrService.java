package com.sample.flickr;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Hakeem on 6/6/17.
 */

public interface FlickrService {
    @GET("/services/rest/")
    Observable<SearchResult> search(@Query("method") String method, @Query("api_key") String key,
                                   @Query("text") String query);

}
