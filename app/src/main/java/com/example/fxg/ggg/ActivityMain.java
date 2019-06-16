package com.example.fxg.ggg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener {
    private TextView item_index, item_goods, item_huodong, item_me,store_name,store_fans;
    private ImageButton button_serach;
    private ViewPager vp;
    private FragmentIndex oneFragment;
    private FragmentGoods twoFragment;
    private FragmentHuodong threeFragment;
    private FragmentShow fouthFragmen;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    String name;
    int fans;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                store_name.setText(name);
                store_fans.setText("粉丝数量"+fans+"万");
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除工具栏
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initViews();

        DBdao dBdao=new DBdao();
        dBdao.start();

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(4);//ViewPager的缓存为4帧
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧
        item_index.setTextColor(Color.parseColor("#66CDAA"));

        //ViewPager的监听事件
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*此方法在页面被选中时调用*/

                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0 ==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
    }

    /**
     * 初始化布局View
     */
    private void initViews() {

        item_index = (TextView) findViewById(R.id.item_index);
        item_goods = (TextView) findViewById(R.id.item_goods);
        item_huodong = (TextView) findViewById(R.id.item_huodong);
        item_me = (TextView) findViewById(R.id.item_me);
        store_name=findViewById(R.id.tv_store_name);
        store_fans=findViewById(R.id.tv_store_fans);
        button_serach=findViewById(R.id.item_search);

        item_index.setOnClickListener(this);
        item_goods.setOnClickListener(this);
        item_huodong.setOnClickListener(this);
        item_me.setOnClickListener(this);
        button_serach.setOnClickListener(this);

        vp = (ViewPager) findViewById(R.id.mainViewPager);
        oneFragment = new FragmentIndex();
        twoFragment = new FragmentGoods();
        threeFragment = new FragmentHuodong();
        fouthFragmen = new FragmentShow();
        //给FragmentList添加数据
        mFragmentList.add(oneFragment);
        mFragmentList.add(twoFragment);
        mFragmentList.add(threeFragment);
        mFragmentList.add(fouthFragmen);


        //goods.setVisibility(View.GONE);
    }

    /**
     * 点击底部Text 动态修改ViewPager的内容
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_index:
                vp.setCurrentItem(0, true);
                break;
            case R.id.item_goods:
                vp.setCurrentItem(1, true);
                break;
            case R.id.item_huodong:
                vp.setCurrentItem(2, true);
                break;
            case R.id.item_me:
                vp.setCurrentItem(3, true);
                break;
            case R.id.item_search:
                Intent intent=new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
        }
    }


    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    /*
     *由ViewPager的滑动修改底部导航Text的颜色
     */
    private void changeTextColor(int position) {
        if (position == 0) {
            //goods.setVisibility(View.GONE);
            item_index.setTextColor(Color.parseColor("#66CDAA"));
            item_goods.setTextColor(Color.parseColor("#000000"));
            item_huodong.setTextColor(Color.parseColor("#000000"));
            item_me.setTextColor(Color.parseColor("#000000"));
        } else if (position == 1) {
            //goods.setVisibility(View.VISIBLE);
            item_goods.setTextColor(Color.parseColor("#66CDAA"));
            item_index.setTextColor(Color.parseColor("#000000"));
            item_huodong.setTextColor(Color.parseColor("#000000"));
            item_me.setTextColor(Color.parseColor("#000000"));
        } else if (position == 2) {
            //goods.setVisibility(View.GONE);
            item_huodong.setTextColor(Color.parseColor("#66CDAA"));
            item_index.setTextColor(Color.parseColor("#000000"));
            item_goods.setTextColor(Color.parseColor("#000000"));
            item_me.setTextColor(Color.parseColor("#000000"));
        } else if (position == 3) {
            //goods.setVisibility(View.GONE);
            item_me.setTextColor(Color.parseColor("#66CDAA"));
            item_index.setTextColor(Color.parseColor("#000000"));
            item_goods.setTextColor(Color.parseColor("#000000"));
            item_huodong.setTextColor(Color.parseColor("#000000"));
        }
    }

    public class DBdao extends Thread{

        public void run(){
            String sql="select * from store where storeId=1";
            try {
                Connection conn=DB.getConnection();
                PreparedStatement pstm=conn.prepareStatement(sql);
                ResultSet rs=pstm.executeQuery();
                if (rs.next()){
                    name=rs.getString("storeName");
                    fans=rs.getInt("storeFans")/10000;
                }
                DB.free(conn,pstm,rs);
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

