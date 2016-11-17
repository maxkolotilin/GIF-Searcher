package com.maximka.gifsearcher.Model;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by maximka on 15.11.16.
 */

public interface GiphyApi {
    String API_KEY = "dc6zaTOxFJmzC";
    String API_ROOT = "http://api.giphy.com";

    @GET("/v1/gifs/trending?api_key=" + API_KEY)
    Observable<GiphyResponse> getTrendingGif(@Query("offset") int offset,
                                             @Query("limit") int limit);

    @GET("/v1/gifs/search?api_key=" + API_KEY)
    Observable<GiphyResponse> searchGif(@Query("q") String searchQuery,
                                        @Query("offset") int offset,
                                        @Query("limit") int limit,
                                        @Query("rating") String rating);
}
