package com.example.fxg.ggg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGoods extends Fragment implements View.OnClickListener{

    View view;
    LinearLayout goods;
    TextView  textView_zonghe,textView_xiaoliang,textView_xinpin,textView_price;
    MyViewPager view_pager_goods;
    Fragment fragment_zonghe,fragment_xiaolaing,fragment_xinpin,fragment_price;
    List<Fragment> my_fragment_list;
    List<TextView> tv_list;
    FragmentPagerAdapter fpa;

    public FragmentGoods() {
        // Required empty public constructor
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_goods, container, false);
        init();

        view_pager_goods.setOffscreenPageLimit(4);//ViewPager的缓存为4帧
        view_pager_goods.setSlide(false);
        view_pager_goods.setAdapter(fpa);
        view_pager_goods.setCurrentItem(0);//初始设置ViewPager选中第一帧
        textView_zonghe.setBackgroundResource(R.color.black);
        textView_zonghe.setTextColor(this.getResources().getColor(R.color.white));
        return view;
    }

    //初始化方法
    private void init(){
        goods=view.findViewById(R.id.goods);

        view_pager_goods=(MyViewPager) view.findViewById(R.id.goods_vp);

        textView_zonghe=view.findViewById(R.id.goods_item_zonghe);
        textView_xiaoliang=view.findViewById(R.id.goods_item_xiaoliang);
        textView_xinpin=view.findViewById(R.id.goods_item_xinpin);
        textView_price=view.findViewById(R.id.goods_item_price);

        tv_list=new ArrayList<TextView>();
        tv_list.add(textView_zonghe);
        tv_list.add(textView_xiaoliang);
        tv_list.add(textView_xinpin);
        tv_list.add(textView_price);

        //设置监听
        textView_zonghe.setOnClickListener(this);
        textView_xiaoliang.setOnClickListener(this);
        textView_xinpin.setOnClickListener(this);
        textView_price.setOnClickListener(this);

        fragment_zonghe=new FragmentGoodsZonghe();
        fragment_xiaolaing=new FragmentGoodsXiaoliang();
        fragment_xinpin=new FragmentGoodsXinpin();
        fragment_price=new FragmentGoodsPrice();

        my_fragment_list=new ArrayList<Fragment>();
        my_fragment_list.add(fragment_zonghe);
        my_fragment_list.add(fragment_xiaolaing);
        my_fragment_list.add(fragment_xinpin);
        my_fragment_list.add(fragment_price);

        fpa=new FragmentViewPagerAdatater(this.getChildFragmentManager(),my_fragment_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_item_zonghe:
                view_pager_goods.setCurrentItem(0, true);
                setTextViewStyle(textView_zonghe);
                break;
            case R.id.goods_item_xiaoliang:
                view_pager_goods.setCurrentItem(1, true);
                setTextViewStyle(textView_xiaoliang);
                break;
            case R.id.goods_item_xinpin:
                view_pager_goods.setCurrentItem(2, true);
                setTextViewStyle(textView_xinpin);
                break;
            case R.id.goods_item_price:
                view_pager_goods.setCurrentItem(3, true);
                setTextViewStyle(textView_price);
                break;
        }
    }
    //改变控件样式
    public void setTextViewStyle(TextView tv){
        for(int i=0;i<tv_list.size();i++){
            if(tv== tv_list.get(i)){
                tv.setBackgroundResource(R.color.black);
                tv.setTextColor(this.getResources().getColor(R.color.white));
            }
            else {
                tv_list.get(i).setBackgroundResource(R.color.white);
                tv_list.get(i).setTextColor(this.getResources().getColor(R.color.black));
            }
        }
    }

    public class FragmentViewPagerAdatater extends FragmentPagerAdapter {

        List<Fragment> fragments_data_list;
        public FragmentViewPagerAdatater(FragmentManager fm,List<Fragment> fragmentList) {
            super(fm);
            this.fragments_data_list=fragmentList;
        }

        @Override
        public Fragment getItem(int i) {
            return fragments_data_list.get(i);
        }

        @Override
        public int getCount() {
            return fragments_data_list.size();
        }
    }



}
