package com.example.fxg.ggg.dao;

import android.os.Handler;

import com.example.fxg.ggg.DB;
import com.example.fxg.ggg.bean.GoodsBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


    public class DBdao extends Thread{
        String sql;
        ResultSet rs;
        List<GoodsBean>goodsBeanList=new ArrayList<GoodsBean>();
        Handler handler=new Handler();
        public DBdao(String sql){
            this.sql=sql;
        }

        public List<GoodsBean> getGoodsBeanList() {
            return goodsBeanList;
        }

        public Handler getHandler() {
            return handler;
        }

        public void run(){
            Connection conn=null;
            try {
                conn=DB.getConnection();
                PreparedStatement pstm=conn.prepareStatement(sql);
                rs=pstm.executeQuery();
                while(rs.next()){
                    GoodsBean goodsBean=new GoodsBean();
                    goodsBean.setPic("http://39.108.181.154:8080/myStorePic/"+rs.getString("goodsPic"));
                    goodsBean.setName(rs.getString("goodsName"));
                    goodsBean.setPrice(Float.toString(rs.getFloat("goodsPrice")));
                    goodsBean.setSales(String.valueOf(rs.getInt("goodsSales")));
                    goodsBeanList.add(goodsBean);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

