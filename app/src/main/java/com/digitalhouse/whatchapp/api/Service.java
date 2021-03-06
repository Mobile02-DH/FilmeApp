package com.digitalhouse.whatchapp.api;

import com.digitalhouse.whatchapp.model.MoviesResponse;
import com.digitalhouse.whatchapp.model.SeriesResponse;
import com.digitalhouse.whatchapp.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("movie/{category}")
    Call<MoviesResponse> getMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page);

    @GET("tv/{category}")
    Call<SeriesResponse> getSeries(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(
            @Path("movie_id")int id,
            @Query("language") String language,
            @Query("api_key") String apiKey);

}
