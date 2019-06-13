package com.example.fxg.ggg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;;
import android.widget.GridView;

import com.example.fxg.ggg.adapter.GoodsAdapter;
import com.example.fxg.ggg.bean.GoodsBean;
import com.example.fxg.ggg.dao.DBdao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGoodsZonghe extends Fragment {


    GridView gv;
    DB db;
    View view;
    GoodsAdapter myAdapter;
    List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
    DBdao dBdao;

    public FragmentGoodsZonghe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_goods_zonghe, container, false);
        init();
        //线程启动
        String sql = "select * from goods;";
        dBdao = new DBdao(sql);
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

    public void init() {
        gv = view.findViewById(R.id.gridView_zonghe);
        db = new DB();
    }

    //根据图片名称获得图片id
    public int getResourceId(String name) {
        try {
            // 根据图片资源的文件名获得Field对象
            Field field = R.drawable.class.getField(name);
            // 取得并返回资源ID
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
        }
        return 0;
    }
}
