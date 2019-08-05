package com.example.fxg.ggg;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fxg.ggg.adapter.CartAdapter;
import com.example.fxg.ggg.bean.GoodsBean;
import com.example.fxg.ggg.dao.DBdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentIndex extends Fragment {
    List<String> indexPic=new ArrayList<>();
    String sql="select * from indexPic;";
    GridView gridView;
    public FragmentIndex() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view= inflater.inflate(R.layout.fragment_index, container, false);
        gridView=view.findViewById(R.id.gridView_index);
        IndexDao indexDao=new IndexDao();
        indexDao.start();
        try {
            indexDao.join();
            IndexPicAdapter indexPicAdapter=new IndexPicAdapter();
            gridView.setAdapter(indexPicAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }
    private class IndexDao extends Thread{
        public void run(){
            Connection conn=null;
            try {
                conn=DB.getConnection();
                PreparedStatement pstm=conn.prepareStatement(sql);
                ResultSet rs=pstm.executeQuery();
                while(rs.next()){
                    String pic="http://39.108.181.154:8080/myStorePic/indexPic/"+rs.getString("indexPicName");
                    indexPic.add(pic);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private class IndexPicAdapter extends BaseAdapter{
        private LayoutInflater inflater = null;

        public IndexPicAdapter() throws SQLException {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return indexPic.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.index_pic_style, parent, false);
                viewHolder.pic = convertView.findViewById(R.id.imageView_index);
                //viewHolder.textView_sales = convertView.findViewById(R.id.goodsSales);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //viewHolder.textView_sales.setText("销量" + goodsBeanList.get(position).getSales());
            ImageLoad1 imageLoad=new ImageLoad1(getActivity(),indexPic.get(position),viewHolder.pic);
            imageLoad.setImage();
            return convertView;
        }
        private class ViewHolder {
            ImageView pic;
        }
    }
}
