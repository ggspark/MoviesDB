package com.fastacash.moviesdb.Fragments;

/**
 * Created by gaurav on 18/11/14.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.fastacash.moviesdb.Activities.MovieDetailsActivity;
import com.fastacash.moviesdb.Activities.MovieListingActivity;
import com.fastacash.moviesdb.Adapters.MovieAdapter;
import com.fastacash.moviesdb.R;
import com.fastacash.moviesdb.models.Result;
import com.fastacash.moviesdb.utils.Constant;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;


public class FragmentSection2 extends BaseFragment {

    private static FragmentSection2 instance = new FragmentSection2();
    private static View rootView;
    private GridView gridView;
    private TextView empty;
    private MovieAdapter movieAdapter;

    private List<Result> dataset;
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


    public static FragmentSection2 getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_section_1, container, false);
        fillUi(rootView);
        Realm realm = Realm.getInstance(this.getActivity());
        dataset = realm.allObjects(Result.class);
        movieAdapter = new MovieAdapter(getActivity(), dataset);
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(movieAdapter);
        swingBottomInAnimationAdapter.setAbsListView(gridView);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(300);
        gridView.setAdapter(swingBottomInAnimationAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Realm realm = Realm.getInstance(this.getActivity());
        dataset = realm.where(Result.class).findAll();

        if (dataset.isEmpty()) {
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }

    private void fillUi(View rootView) {
        empty = (TextView) rootView.findViewById(R.id.empty);
        gridView = (GridView) rootView.findViewById(R.id.movie_gridview);

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (getActivity() instanceof MovieListingActivity) {
                    if (firstVisibleItem == 0) {
                        ((MovieListingActivity) getActivity()).swipeRefreshLayout.setEnabled(true);
                    } else {
                        ((MovieListingActivity) getActivity()).swipeRefreshLayout.setEnabled(false);
                    }
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra(Constant.MOVIE, gson.toJson(new Result(dataset.get(position))));
                startActivity(intent);
            }
        });
    }

    public void notifyDataSetChanged() {
        if (movieAdapter != null) {
            movieAdapter.notifyDataSetChanged();
        }
    }
}
