package com.randerson.mendes.tmdbandroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private List<Genre> allGenres;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public MoviesAdapter(List<Movie> movies, List<Genre> allGenres){
        this.movies = movies;
        this.allGenres = allGenres;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    //contagem de itens mostrados na tela
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void appendMovies(List<Movie> moviesToAppend){
        movies.addAll(moviesToAppend);
        notifyDataSetChanged();
    }

    //class separada
    class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView txtReleaseDate, txtTitle, txtRating, txtGenres;
        ImageView poster;


        public MovieViewHolder(View v) {

            super(v);
            txtReleaseDate = itemView.findViewById(R.id.item_movie_release_date);
            txtTitle = itemView.findViewById(R.id.item_movie_title);
            txtRating = itemView.findViewById(R.id.item_movie_rating);
            txtGenres = itemView.findViewById(R.id.item_movie_genre);
            poster = itemView.findViewById(R.id.item_movie_poster);

        }

        //método para setar os valores de cada textview
        public void bind(Movie movie){
            txtReleaseDate.setText(movie.getReleaseDate().split("-")[0]);
            txtTitle.setText(movie.getTitle());
            txtRating.setText(String.valueOf(movie.getRating()));
            txtGenres.setText(getGenres(movie.getGenreIds()));
            Glide.with(itemView).load(IMAGE_BASE_URL + movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary)).into(poster);
        }

        private String getGenres(List<Integer> genreIds){
            List<String> movieGenres = new ArrayList<>();
            for(Integer genreId: genreIds) {
                for (Genre genre: allGenres) {
                    if(genre.getId() == genreId) {
                        movieGenres.add(genre.getName());
                        break;
                    }
                }
            }
            return TextUtils.join(", ", movieGenres);
        }
    }

}
