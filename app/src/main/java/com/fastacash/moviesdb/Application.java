package com.fastacash.moviesdb;


import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
/**
 * @author Gaurav Gupta <gaurav@thegauravgupta.com>
 * @since 24/Dec/2014
 */

public class Application extends android.app.Application {

    public Application() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_cloud_download_black_48dp)
                .showImageForEmptyUri(R.drawable.ic_camera_alt_black_48dp)
                .showImageOnFail(R.drawable.ic_error_outline_black_48dp)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).
                threadPoolSize(5)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);

    }
}