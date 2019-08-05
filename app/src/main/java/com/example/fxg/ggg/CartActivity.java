package com.example.fxg.ggg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.example.fxg.ggg.adapter.CartAdapter;
import com.example.fxg.ggg.bean.GoodsBean;
import com.example.fxg.ggg.dao.DBdao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    ListView listView;
    GridView gridView;
    List<GoodsBean>goodsBeanList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        String sql="SELECT * FROM goods INNER JOIN fxg WHERE fxg.goods_ID=goods.goodsId;";
        DBdao dBdao=new DBdao(sql);
        dBdao.start();
        try {
            dBdao.join();
            goodsBeanList=dBdao.getGoodsBeanList();
            CartAdapter cartAdapter=new CartAdapter(this,goodsBeanList);
            gridView.setAdapter(cartAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void init(){
       // listView=findViewById(R.id.listView_cart);
        gridView=findViewById(R.id.gridView_cart);
    }
}