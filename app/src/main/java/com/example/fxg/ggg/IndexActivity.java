package com.example.fxg.ggg;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fxg.ggg.adapter.MyRecycAdapter;
import com.example.fxg.ggg.adapter.ZhiBoRecycAdapter;
import com.example.fxg.ggg.bean.UserBean;
import com.example.fxg.ggg.bean.VideoBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.provider.MediaStore;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    TextView zhibo,textView_fx,textView_gz,textView_lx,textView_hx1,textView_hx2,textView_hx3;
    FragmentFind fragmentFind=new FragmentFind();
    FragmentGuanzhu fragmentGuanzhu=new FragmentGuanzhu();
    FragmentPopular fragmentPopular=new FragmentPopular();
    FragmentGoods.FragmentViewPagerAdatater fragmentViewPagerAdatater;
    List<Fragment> fragmentList=new ArrayList<>();
    TextView index_shop,index_my,textView_xiangji,textView_zhibo,textView8;
    MD5Utils userid=new MD5Utils();
    RecyclerView recyclerView;
    Connection conn;
    ResultSet rs;
    PreparedStatement pstm;
    List<UserBean>userBeanList=new ArrayList<>();
    ZhiboDao zhiboDao=new ZhiboDao();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                try {
                    zhiboDao.setUserBeanList();
                    ZhiBoRecycAdapter myRecycAdapter=new ZhiBoRecycAdapter(IndexActivity.this,userBeanList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(IndexActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    //设置布局管理器
                    recyclerView.setLayoutManager(layoutManager);
                    //设置为垂直布局，这也是默认的
                    layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                    //设置Adapter
                    recyclerView.setAdapter( myRecycAdapter);
                    myRecycAdapter.setOnClickListener(new ZhiBoRecycAdapter.OnClickListener() {
                        @Override
                        public void onClick(View itemView, int position) {
                            Intent intent=new Intent(IndexActivity.this,VideoActivity.class);
                            intent.putExtra("url","rtmp://39.108.181.154/live/"+userBeanList.get(position).getUsrName());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        init();
        Thread thread=new Thread(zhiboDao);
        thread.start();
        fragmentViewPagerAdatater=new FragmentGoods.FragmentViewPagerAdatater(getSupportFragmentManager(),fragmentList);
        viewPager.setOffscreenPageLimit(3);//ViewPager的缓存为4帧
        //viewPager.setSlide(false);
        viewPager.setAdapter(fragmentViewPagerAdatater);
        viewPager.setCurrentItem(0);//初始设置ViewPager选中第一帧
        //ViewPager的监听事件
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*此方法在页面被选中时调用*/
                switch (position){
                    case 0:
                        textView_hx1.setVisibility(View.VISIBLE);
                        textView_hx2.setVisibility(View.INVISIBLE);
                        textView_hx3.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        textView_hx1.setVisibility(View.INVISIBLE);
                        textView_hx2.setVisibility(View.VISIBLE);
                        textView_hx3.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        textView_hx1.setVisibility(View.INVISIBLE);
                        textView_hx2.setVisibility(View.INVISIBLE);
                        textView_hx3.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0 ==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
//        zhibo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),VideoActivity.class);
//                String url = "rtmp://39.108.181.154/live/123";
//                intent.putExtra("url",url);
//                startActivity(intent);
//            }
//        });
        index_shop.setOnClickListener(this);
        index_my.setOnClickListener(this);
    }

    private void init(){
        recyclerView=findViewById(R.id.recyclerview_index_zhibo);
        viewPager=findViewById(R.id.viewpager_index_video);
        textView_fx=findViewById(R.id.textView_index_fx);
        textView_gz=findViewById(R.id.textView_index_gz);
        textView_lx=findViewById(R.id.textView_index_lx);
        textView_hx1=findViewById(R.id.textView_index_hx1);
        textView_hx2=findViewById(R.id.textView_index_hx2);
        textView_hx3=findViewById(R.id.textView_index_hx3);
        textView_hx1.setVisibility(View.VISIBLE);
        textView_hx2.setVisibility(View.INVISIBLE);
        textView_hx3.setVisibility(View.INVISIBLE);

        textView8=findViewById(R.id.textView8);
        textView8.setOnClickListener(this);

        textView_fx.setOnClickListener(this);
        textView_gz.setOnClickListener(this);
        textView_lx.setOnClickListener(this);

        //zhibo=findViewById(R.id.zhibo);
        index_shop=findViewById(R.id.textView_shangcheng);
        textView_xiangji=findViewById(R.id.textView_xiangji);
        textView_xiangji.setOnClickListener(this);
        textView_zhibo=findViewById(R.id.textView_zhibo);
        textView_zhibo.setOnClickListener(this);
        index_my=findViewById(R.id.textView_wode);

        fragmentList.add(fragmentFind);
        fragmentList.add(fragmentGuanzhu);
        fragmentList.add(fragmentPopular);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView_shangcheng:
                Intent intent=new Intent(this,ActivityMain.class);
                startActivity(intent);
                break;
            case R.id.textView_index_fx:
                viewPager.setCurrentItem(0);
                textView_hx1.setVisibility(View.VISIBLE);
                textView_hx2.setVisibility(View.INVISIBLE);
                textView_hx3.setVisibility(View.INVISIBLE);
                break;
            case R.id.textView_index_gz:
                viewPager.setCurrentItem(1);
                textView_hx1.setVisibility(View.INVISIBLE);
                textView_hx2.setVisibility(View.VISIBLE);
                textView_hx3.setVisibility(View.INVISIBLE);
                break;
            case R.id.textView_index_lx:
                viewPager.setCurrentItem(2);
                textView_hx1.setVisibility(View.INVISIBLE);
                textView_hx2.setVisibility(View.INVISIBLE);
                textView_hx3.setVisibility(View.VISIBLE);
                break;
            case R.id.textView_wode:
                userid.write();
                jdbc relogin=new jdbc();
                int i=relogin.relogin();
                if (i==1)//若无登陆信息则跳转至登录页面，有则跳转至用户中心页面
                {
                    startActivity(new Intent(this, UserActivity.class));
                }
                else
                {

                    startActivity(new Intent(this,LoginActivity.class));
                }
                //startActivity(new Intent(this,MyActivity.class));
                break;
            case R.id.textView_xiangji:
                startActivity(new Intent(this,CameraActivity.class));
                break;
            case R.id.textView_zhibo:
                startActivity(new Intent(this,ZhiboActivity.class));
                break;
            case R.id.textView8:
                Intent intent1=new Intent(this,VideoActivity.class);
                intent1.putExtra("url","rtmp://39.108.181.154/live/123");
                startActivity(intent1);
                break;
        }
    }

    private class ZhiboDao implements Runnable{
        String sql="select user.userId,user.userName from user INNER JOIN zhibo on zhibo.userName=user.userName;";

        private void setUserBeanList() throws SQLException {
            while (rs.next()){
                UserBean userBean=new UserBean();
                userBean.setUsrId(rs.getInt("userId"));
                userBean.setUsrName(rs.getString("userName"));
                userBeanList.add(userBean);
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
