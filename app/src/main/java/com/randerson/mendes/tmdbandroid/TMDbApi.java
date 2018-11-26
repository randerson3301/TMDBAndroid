package com.randerson.mendes.tmdbandroid;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbApi {

    @GET("discover/movie")
    Call<MovieResponse> getPopularMovies(
            //parametros necessarios
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    //metodo para retornar os generos
    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(
            //parametros necessarios
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

}
