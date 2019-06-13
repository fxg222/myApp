package com.example.fxg.ggg;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager{

    boolean isSlide=false;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }
    public MyViewPager(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    public void setSlide(boolean slide){
        this.isSlide=slide;
    }
    public boolean onInterceptTouchEvent(MotionEvent ev){
        return isSlide;
    }
    public boolean onTouchEvent(MotionEvent ev){
        return isSlide;
    }
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, false);
    }
}
