package com.fastacash.moviesdb;

import com.fastacash.moviesdb.models.Result;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Gaurav Gupta <gaurav@thegauravgupta.com>
 * @since 02/Jul/2015
 */
public class DataSingleton {
    private static DataSingleton instance;
    private List<Result> nowPlayingMovies = new LinkedList<Result>();

    public static DataSingleton getInstance(){
        if(instance==null) {
            instance = new DataSingleton();
            instance.nowPlayingMovies = new ArrayList<Result>();
        }

        return instance;
    }

    public List<Result> getNowPlayingMovies() {
        return nowPlayingMovies;
    }

    public void setNowPlayingMovies(List<Result> nowPlayingMovies) {
        this.nowPlayingMovies = nowPlayingMovies;
    }
}
