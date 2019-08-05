package com.example.fxg.ggg;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoad2 {
    Context context;
    ImageLoaderConfiguration configuration;
    private DisplayImageOptions options = null;
    private ImageLoader imageLoader = null;
    String url;
    ImageView imageView;

    public ImageLoad2(Context context,String url,ImageView imageView){
        this.context=context;
        this.url=url;
        this.imageView=imageView;
        configuration= ImageLoaderConfiguration.createDefault(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        imageLoader = ImageLoader.getInstance();
    }
    public void setImage(){
        imageLoader.init(configuration);
        imageLoader.displayImage(url+".PNG", imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }
            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }
            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }
}


