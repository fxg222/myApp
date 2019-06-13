package com.example.fxg.ggg.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class DetailsPicAdapter extends PagerAdapter {
    private List<ImageView>imageViewList;

    public DetailsPicAdapter(List<ImageView>imageViewList){
        this.imageViewList=imageViewList;
    }
    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=imageViewList.get(position);
        container.addView(imageView);
        return imageView;
    }
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(imageViewList.get(position));
    }
}
