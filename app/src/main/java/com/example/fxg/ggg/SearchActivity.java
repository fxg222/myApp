package com.example.fxg.ggg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private EditText search;                        //搜索框控件
    private TextView mian_bao;                      //'面包'控件
    private TextView mei_ri_jian_guo;               //'每日坚果'控件
    private TextView zhu_rou_pu;                    //'猪肉铺'控件
    private TextView mang_guo_gan;                  //'芒果干'控件
    private TextView ling_shi_da_li_bao;            //'零食大礼包'控件
    private TextView xin_pin;                       //'新品'控件
    private TextView ling_shi;                      //'零食'控件
    private TextView man_jian;                      //'满减'控件
    private String search_value="search_value";     //Activity传值变量
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findView();
        setSearchListener();                //搜索监听
        setMian_baoListener();              //搜索‘面包’监听
        setMei_ri_jian_guoListener();       //搜索‘每日坚果’监听
        setZhu_rou_puListener();            //搜索‘猪肉铺’监听
        setMang_guo_ganListener();          //搜索‘芒果干’监听
        setLing_shi_da_li_baoListener();    //搜索‘零食大礼包’监听
        setXin_pinListener();               //搜索‘新品’监听
        setLing_shiListener();              //搜索‘零食’监听
        setMan_jianListener();              //搜索‘满减’监听

    }
    //搜索监听
    private void setSearchListener(){
        Button ib = super.findViewById(R.id.button_search_search);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SearchActivity.this,MainActivity.class));
                by_value(search_value,search.getText().toString());          //将搜索值传到另一个Activity
            }
        });
    }

    //搜索‘面包’监听
    private void setMian_baoListener(){
        TextView tv=super.findViewById(R.id.textView_mianbao);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SearchActivity.this,ActivityMain.class));
                by_value(search_value,mian_bao.getText().toString());
            }
        });
    }

    //搜索‘每日坚果’监听
    private void setMei_ri_jian_guoListener(){
        TextView tv=super.findViewById(R.id.textView_meirijianguo);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SearchActivity.this,ActivityMain.class));
                by_value(search_value,mei_ri_jian_guo.getText().toString());
            }
        });
    }

    //搜索‘猪肉铺’监听
    private void setZhu_rou_puListener(){
        TextView tv=super.findViewById(R.id.textView_zhuroupu);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SearchActivity.this,ActivityMain.class));
                by_value(search_value,zhu_rou_pu.getText().toString());
            }
        });
    }

    //搜索‘芒果干’监听
    private void setMang_guo_ganListener(){
        TextView tv=super.findViewById(R.id.textView_mangguogan);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SearchActivity.this,MainActivity.class));
                by_value(search_value,mang_guo_gan.getText().toString());
            }
        });
    }

    //搜索‘零食大礼包’监听
    private void setLing_shi_da_li_baoListener(){
        TextView tv=super.findViewById(R.id.textView_lingshidalibao);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SearchActivity.this,MainActivity.class));
                by_value(search_value,ling_shi_da_li_bao.getText().toString());
            }
        });
    }

    //搜索‘新品’监听
    private void setXin_pinListener(){
        TextView tv=super.findViewById(R.id.textView_xinpin);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SearchActivity.this,MainActivity.class));
                by_value(search_value,xin_pin.getText().toString());
            }
        });
    }

    //搜索‘零食’监听
    private void setLing_shiListener(){
        TextView tv=super.findViewById(R.id.textView_lingshi);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SearchActivity.this,MainActivity.class));
                by_value(search_value,ling_shi.getText().toString());
            }
        });
    }

    //搜索‘满减’监听
    private void setMan_jianListener(){
        TextView tv=super.findViewById(R.id.textView_manjian);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SearchActivity.this,MainActivity.class));
                by_value(search_value,man_jian.getText().toString());
            }
        });
    }

    @SuppressLint("WrongViewCast")
    //查找页面中的控件
    public void findView(){
        search=(EditText) findViewById(R.id.search_editText_search);                  //搜索框控件
        mian_bao=(TextView) findViewById(R.id.textView_mianbao);                    //‘面包’控件
        mei_ri_jian_guo=(TextView)findViewById(R.id.textView_meirijianguo);         //‘每日坚果’控件
        zhu_rou_pu=(TextView)findViewById(R.id.textView_zhuroupu);                  //‘猪肉铺’控件
        mang_guo_gan=(TextView)findViewById(R.id.textView_mangguogan);              //‘芒果干’控件
        ling_shi_da_li_bao=(TextView)findViewById(R.id.textView_lingshidalibao);    //‘零食大礼包’控件
        xin_pin=(TextView)findViewById(R.id.textView_xinpin);                       //‘新品’控件
        ling_shi=(TextView)findViewById(R.id.textView_lingshi);                     //‘零食’控件
        man_jian=(TextView)findViewById(R.id.textView_manjian);                     //‘满减’控件
    }

    //在两个Activity之间传值
    public void by_value(String name,String value){
        Intent i=new Intent(SearchActivity.this,SearchGoodsActivity.class);
        i.putExtra(name,value);
        startActivity(i);
    }

}
