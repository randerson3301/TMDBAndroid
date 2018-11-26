package com.randerson.mendes.tmdbandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

   private RecyclerView rv;
   private MoviesAdapter adapter;
   private boolean isFetchingMovies;
   private int currentPage = 1;
   private List<Genre> movieGenres;

   private MoviesRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = MoviesRepository.getInstance();

        rv = (RecyclerView) findViewById(R.id.movies_list);
        //é necessário adicinar ao RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));


        getGenres();

    }

    private void getGenres(){
        repository.getGenres(new OnGetGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                movieGenres = genres;
                getMovies(currentPage);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void setupOnScrollListener(){
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstCompletelyVisibleItemPosition();

                if(firstVisibleItem + visibleItemCount >= totalItemCount / 2){
                    if(!isFetchingMovies) {
                        getMovies(currentPage + 1);
                    }
                }
            }
        });
    }

    private void getMovies(int page){
        isFetchingMovies = true;
        repository.getMovies(page, new OnGetMoviesCallback() {
            @Override
            public void onSucess(int page, List<Movie> movies) {
                if(adapter == null) {
                    adapter = new MoviesAdapter(movies, movieGenres);
                    rv.setAdapter(adapter);
                } else {
                    adapter.appendMovies(movies);
                }
                currentPage = page;
                isFetchingMovies = false;
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }
}
