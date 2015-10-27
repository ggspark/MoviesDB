package com.fastacash.moviesdb.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fastacash.moviesdb.DataSingleton;
import com.fastacash.moviesdb.R;
import com.fastacash.moviesdb.models.Result;
import com.fastacash.moviesdb.utils.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * @author Gaurav Gupta <gaurav@thegauravgupta.com>
 * @since 29/Jun/2015
 */
public class MovieAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Result> dataset;

    public MovieAdapter(Context context) {
        dataset = DataSingleton.getInstance().getNowPlayingMovies();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public Object getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_movie, null);
            holder.image = (ImageView) convertView.findViewById(R.id.short_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage("http://image.tmdb.org/t/p/w500/"+ dataset.get(position).getPosterPath()+"?api_key="+ Constant.API_KEY, holder.image);

        return convertView;
    }

    class ViewHolder {
        ImageView image;
    }
}