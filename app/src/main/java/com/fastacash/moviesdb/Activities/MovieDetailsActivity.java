package com.fastacash.moviesdb.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fastacash.moviesdb.Adapters.MovieAdapter;
import com.fastacash.moviesdb.R;
import com.fastacash.moviesdb.controller.APIServices;
import com.fastacash.moviesdb.models.Movie;
import com.fastacash.moviesdb.models.Result;
import com.fastacash.moviesdb.utils.Constant;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieDetailsActivity extends BaseActivity {

    private GridView gridView;
    private MovieAdapter movieAdapter;
    private List<Result> dataset;
    private Realm realm;
    private RealmResults<Result> results;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        String res = getIntent().getStringExtra(Constant.MOVIE);
        final Result movie = gson.fromJson(res, Result.class);

        ImageView backdrop = (ImageView) findViewById(R.id.backdrop);
        ImageView poster = (ImageView) findViewById(R.id.poster);
        TextView title = (TextView) findViewById(R.id.title);
        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setMovementMethod(new ScrollingMovementMethod());
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fav_btn);
        realm = Realm.getInstance(MovieDetailsActivity.this);
        results = realm.where(Result.class).equalTo("id", movie.getId()).findAll();
        if (!results.isEmpty()) {
            fab.setImageResource(R.drawable.ic_ic_favorite_yellow_24dp);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.beginTransaction();
                if (results.isEmpty()) {
                    realm.copyToRealm(movie);
                    fab.setImageResource(R.drawable.ic_ic_favorite_yellow_24dp);
                    Toast.makeText(MovieDetailsActivity.this, "Movie added to Favourites", Toast.LENGTH_SHORT).show();
                } else {
                    results.remove(0);
                    Drawable d = getResources().getDrawable(R.drawable.ic_favorite_white_24dp);
                    fab.setImageDrawable(d);
                    Toast.makeText(MovieDetailsActivity.this, "Movie removed from Favourites", Toast.LENGTH_SHORT).show();
                }
                realm.commitTransaction();
                results = realm.where(Result.class).equalTo("id", movie.getId()).findAll();
            }
        });


        ImageLoader.getInstance().displayImage("http://image.tmdb.org/t/p/w500/" + movie.getBackdropPath() + "?api_key=" + Constant.API_KEY, backdrop);
        ImageLoader.getInstance().displayImage("http://image.tmdb.org/t/p/w500/" + movie.getPosterPath() + "?api_key=" + Constant.API_KEY, poster);
        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());

        fillUi();
        dataset = new ArrayList<Result>();
        movieAdapter = new MovieAdapter(this, dataset);
        queryServer(movie.getId());
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(movieAdapter);
        swingBottomInAnimationAdapter.setAbsListView(gridView);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(300);
        gridView.setAdapter(swingBottomInAnimationAdapter);


    }

    private void fillUi() {
        gridView = (GridView) findViewById(R.id.movie_gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MovieDetailsActivity.this, MovieDetailsActivity.class);
                intent.putExtra(Constant.MOVIE, gson.toJson(dataset.get(position)));
                startActivity(intent);
                finish();
            }
        });
    }

    synchronized void queryServer(final int id) {
        loadingDialog.show();
        APIServices.getMovieService().getSimilar(id, Constant.API_KEY, 1, new Callback<Movie>() {
            @Override
            public void success(Movie movie, Response response) {
                dataset.clear();
                dataset.addAll(movie.getResults());
                movieAdapter.notifyDataSetChanged();
                loadingDialog.dismiss();
                for (int i = 2; i <= movie.getTotalPages(); i++) {
                    loadPage(i, id);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                loadingDialog.dismiss();
                Toast.makeText(MovieDetailsActivity.this, "Error loading related movies", Toast.LENGTH_SHORT).show();
            }
        });
    }


    synchronized void loadPage(int number, final int id) {
        APIServices.getMovieService().getSimilar(id, Constant.API_KEY, number, new Callback<Movie>() {
            @Override
            public void success(Movie movie, Response response) {
                dataset.addAll(movie.getResults());
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

}
