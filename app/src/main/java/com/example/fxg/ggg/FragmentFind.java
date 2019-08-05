package com.example.fxg.ggg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fxg.ggg.adapter.MyRecycAdapter;
import com.example.fxg.ggg.bean.VideoBean;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FragmentFind extends Fragment {
    VideoDao videoDao;
    Connection conn;
    ResultSet rs;
    PreparedStatement pstm;
    View view;
    RecyclerView recyclerView;
    List<VideoBean>videoBeanList=new ArrayList<>();

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                try {
                    videoDao.setVideoBeanList();
                    MyRecycAdapter myRecycAdapter=new MyRecycAdapter(getActivity(),videoBeanList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    GridLayoutManager grid = new GridLayoutManager(getContext(), 3);
                    //设置布局管理器
                    recyclerView.setLayoutManager(grid);
                    //设置为垂直布局，这也是默认的
                    layoutManager.setOrientation(OrientationHelper.VERTICAL);
                    //设置Adapter
                    recyclerView.setAdapter( myRecycAdapter);
                    myRecycAdapter.setOnClickListener(new MyRecycAdapter.OnClickListener() {
                        @Override
                        public void onClick(View itemView, int position) {
                            Intent intent=new Intent(getContext(),VideoActivity.class);
                            intent.putExtra("url","rtmp://39.108.181.154/vod/"+videoBeanList.get(position).getVideoName());
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(View itemView, int position) {

                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    public FragmentFind() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_find, container, false);
        recyclerView=view.findViewById(R.id.recyclerview);

        videoDao=new VideoDao();
        Thread thread=new Thread(videoDao);
        thread.start();
        return view;
    }

    private class VideoDao implements Runnable{
        String sql="select * from video;";

        private void setVideoBeanList() throws SQLException {
            while (rs.next()){
                VideoBean videoBean=new VideoBean();
                videoBean.setVideoName(rs.getString("videoName"));
                videoBean.setVideoPicUrl("http://39.108.181.154:8080/myStorePic/videoPic/"+rs.getString("videoPic"));
                videoBean.setVideoText(rs.getString("videoText").substring(0,10));
                videoBean.setVideoUserName(rs.getString("userName"));
                videoBeanList.add(videoBean);
            }
            rs.first();
        }

        @Override
        public void run() {
            try {
                conn = DB.getConnection();
                pstm=conn.prepareStatement(sql);
                rs=pstm.executeQuery();
                Message msg=new Message();
                msg.what=0;
                handler.sendMessage(msg);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
