package com.example.fxg.ggg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.fxg.ggg.adapter.GoodsAdapter;
import com.example.fxg.ggg.bean.GoodsBean;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGoodsXiaoliang extends Fragment {

    GridView gv;
    DB db;
    View view;
    GoodsAdapter myAdapter;
    List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
    com.example.fxg.ggg.dao.DBdao dBdao;

    public FragmentGoodsXiaoliang() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_goods_xiaoliang, container, false);
        init();
        //线程启动
        String sql = "select * from goods order by goodsSales desc;";
        dBdao = new com.example.fxg.ggg.dao.DBdao(sql);
        dBdao.start();
        try {
            dBdao.join();
            goodsBeanList = (List<GoodsBean>) dBdao.getGoodsBeanList();
            myAdapter = new GoodsAdapter(getActivity(), goodsBeanList);
            gv.setAdapter(myAdapter);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), ActivityDetails.class);
                    GoodsBean goods = goodsBeanList.get(position);
                    intent.putExtra("goods", goods);
                    startActivity(intent);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void init(){
        //tv=view.findViewById(R.id.bbb);
        gv=view.findViewById(R.id.gridView_xiaoliang);
        db=new DB();
    }
}