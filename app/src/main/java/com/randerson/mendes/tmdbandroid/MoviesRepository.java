package com.randerson.mendes.tmdbandroid;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "pt";

    private static MoviesRepository repository;

    private TMDbApi api;

    //construtor
    private MoviesRepository(TMDbApi api) {
        this.api = api;
    }

    public static MoviesRepository getInstance(){
        if(repository == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            repository = new MoviesRepository(retrofit.create(TMDbApi.class));
        }

        return repository;
    }

    //método para conectar com os filmes populares
    public void getMovies(int page, final OnGetMoviesCallback callback){
        Log.e("MoviesRepository", "Next Page=" + page);
        api.getPopularMovies("8dd702446c98959288bf81386e153c8c",
                LANGUAGE, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();

                    if(movieResponse != null && movieResponse.getMovies() != null) {
                        callback.onSucess(movieResponse.getPage(), movieResponse.getMovies());
                    } else {
                        callback.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onError();
            }
        });
    }

    //método para retornas os generos da api
    public void getGenres(final OnGetGenresCallback callback){
        api.getGenres("8dd702446c98959288bf81386e153c8c", LANGUAGE).enqueue(
                new Callback<GenreResponse>() {
                    @Override
                    public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                        if(response.isSuccessful()) {
                            GenreResponse genreResponse = response.body();

                            if(genreResponse != null && genreResponse.getGenres() != null) {
                                callback.onSuccess(genreResponse.getGenres());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenreResponse> call, Throwable t) {
                        callback.onError();
                    }
                }
        );
    }
}
