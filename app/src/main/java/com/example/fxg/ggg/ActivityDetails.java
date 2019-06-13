package com.example.fxg.ggg;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fxg.ggg.adapter.DetailsPicAdapter;
import com.example.fxg.ggg.bean.GoodsBean;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityDetails extends AppCompatActivity {
    ImageView imageView1,imageView2,imageView3;
    List<ImageView>imageViewList=new ArrayList<>();
    TextView tv_goods_name,tv_goods_sales;
    String name;
    float sales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除工具栏
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_details);
        init();
        GoodsBean goodsBean=(GoodsBean) getIntent().getExtras().get("goods");
        ImageLoad imageLoad=new ImageLoad(this,goodsBean.getPic(),imageView1);
        imageLoad.setImage();
        ImageLoad imageLoad1=new ImageLoad(this,goodsBean.getPic()+"_1",imageView2);
        imageLoad1.setImage();
        ImageLoad imageLoad2=new ImageLoad(this,goodsBean.getPic()+"_2",imageView3);
        imageLoad2.setImage();
        imageViewList.add(imageView1);
        imageViewList.add(imageView2);
        imageViewList.add(imageView3);
        DetailsPicAdapter adapter=new DetailsPicAdapter(imageViewList);
        ViewPager viewPager=(ViewPager) findViewById(R.id.viewPager_details_pic);
        viewPager.setAdapter(adapter);
        tv_goods_name.setText(goodsBean.getName());
        tv_goods_sales.setText("销量"+goodsBean.getSales());
    }
    public void init(){
        //imageView1=findViewById(R.id.imageView_details_pic);
        imageView1=new ImageView(this);
        imageView2=new ImageView(this);
        imageView3=new ImageView(this);
        tv_goods_name=findViewById(R.id.textView_details_goodsName);
        tv_goods_sales=findViewById(R.id.textView_details_goodsSales);
    }
}
