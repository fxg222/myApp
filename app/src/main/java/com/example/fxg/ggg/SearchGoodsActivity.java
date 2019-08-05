package com.example.fxg.ggg;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SearchGoodsActivity extends AppCompatActivity{
    String goods_name;
    FragmentGoods fragmentGoods;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                FragmentManager fm=getSupportFragmentManager();
                fm.beginTransaction().add(R.id.frameLayout_searchGoods_fl,fragmentGoods).commit();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);
        init();
        mythread th=new mythread();
        Thread thread=new Thread(th);
        thread.start();
//        fragmentGoods=new FragmentGoods(goods_name);
//        FragmentManager fm=getSupportFragmentManager();
//        fm.beginTransaction().add(R.id.frameLayout_searchGoods_fl,fragmentGoods).commit();
    }
    protected void onStart(){
        super.onStart();
        //init();
//        fragmentGoods=new FragmentGoods(goods_name);
//        FragmentManager fm=getSupportFragmentManager();
//        fm.beginTransaction().add(R.id.frameLayout_searchGoods_fl,fragmentGoods).commit();
    }
    protected void onResume() {
        super.onResume();
        //fragmentGoods=new FragmentGoods(goods_name);
//        FragmentManager fm=getSupportFragmentManager();
//        fm.beginTransaction().add(R.id.frameLayout_searchGoods_fl,fragmentGoods).commit();
    }
    public void init(){
        goods_name=getIntent().getStringExtra("search_value");
    }

    private class mythread implements Runnable{

        @Override
        public void run() {
            Looper.prepare();
            fragmentGoods=new FragmentGoods(goods_name);
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
            Looper.loop();
        }
    }
}
