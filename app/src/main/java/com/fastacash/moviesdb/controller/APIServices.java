package com.fastacash.moviesdb.controller;


import com.fastacash.moviesdb.models.Movie;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Gaurav Gupta <gaurav@thegauravgupta.com>
 * @since 27/May/2015
 */
public class APIServices {
    private static Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();


    static final String API_URL="http://api.themoviedb.org/3";

    //Create a rest adapter with our settings
    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(API_URL)
            .setConverter(new GsonConverter(gson))
            .build();

    //Create a service interface to query API
    public interface Movies {
        @GET("/movie/now_playing")
        void getNowPlaying(@Query("api_key") String key, @Query("page") int page, Callback<Movie> cb);

        @GET("/movie/{id}/similar")
        void getSimilar(@Path("id") int movieId, @Query("api_key") String key, @Query("page") int page, Callback<Movie> cb);
    }

    private static final Movies MOVIE_SERVICE = REST_ADAPTER.create(Movies.class);


    public static Movies getMovieService() {
        return MOVIE_SERVICE;
    }
}

