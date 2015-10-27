package com.fastacash.moviesdb.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fastacash.moviesdb.Adapters.MovieAdapter;
import com.fastacash.moviesdb.DataSingleton;
import com.fastacash.moviesdb.R;
import com.fastacash.moviesdb.models.Result;
import com.fastacash.moviesdb.utils.Constant;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MovieDetailsActivity extends BaseActivity {

    private GridView gridView;
    private MovieAdapter movieAdapter;
    private List<Result> dataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        int pos = getIntent().getIntExtra(Constant.MOVIE, -1);
        Result movie = new Result();
        if(pos>=0){
            movie = DataSingleton.getInstance().getNowPlayingMovies().get(pos);
        }


        ImageView backdrop = (ImageView) findViewById(R.id.backdrop);
        ImageView poster = (ImageView) findViewById(R.id.poster);
        TextView title = (TextView) findViewById(R.id.title);
        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setMovementMethod(new ScrollingMovementMethod());


        ImageLoader.getInstance().displayImage("http://image.tmdb.org/t/p/w500/" + movie.getBackdropPath() + "?api_key=" + Constant.API_KEY, backdrop);
        ImageLoader.getInstance().displayImage("http://image.tmdb.org/t/p/w500/" + movie.getPosterPath() + "?api_key=" + Constant.API_KEY, poster);
        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());

        fillUi();
        dataset = DataSingleton.getInstance().getNowPlayingMovies();
        movieAdapter = new MovieAdapter(this, dataset);
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
                intent.putExtra(Constant.MOVIE, position);
                startActivity(intent);
            }
        });
    }
}
