package com.randerson.mendes.tmdbandroid;

import java.util.List;

public interface OnGetMoviesCallback {

    void onSucess(int page, List<Movie> movies);

    void onError();
}
