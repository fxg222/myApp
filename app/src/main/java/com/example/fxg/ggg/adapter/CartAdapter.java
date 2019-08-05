package com.example.fxg.ggg.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fxg.ggg.ImageLoad;
import com.example.fxg.ggg.R;
import com.example.fxg.ggg.bean.GoodsBean;

import java.sql.SQLException;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Activity my_activity;
    private LayoutInflater inflater = null;
    private List<GoodsBean> goodsBeanList = null;

    public CartAdapter(Activity activity, List<GoodsBean> goodsBeanList) throws SQLException {
        this.my_activity = activity;
        this.goodsBeanList = goodsBeanList;
        inflater = (LayoutInflater) this.my_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return goodsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CartAdapter.ViewHolder viewHolder = new CartAdapter.ViewHolder();
        if (convertView == null) {
        convertView = inflater.inflate(R.layout.cart_style, parent, false);
        viewHolder.pic = convertView.findViewById(R.id.imageView_cart_pic);
        viewHolder.textView_name = convertView.findViewById(R.id.textView_cart_name);
        viewHolder.textView_price = convertView.findViewById(R.id.textView_cart_price);
        //viewHolder.textView_sales = convertView.findViewById(R.id.goodsSales);
        convertView.setTag(viewHolder);
    } else {
        viewHolder = (CartAdapter.ViewHolder) convertView.getTag();
    }
        viewHolder.textView_name.setText(goodsBeanList.get(position).getName());
        viewHolder.textView_price.setText("￥" + goodsBeanList.get(position).getPrice());
    //viewHolder.textView_sales.setText("销量" + goodsBeanList.get(position).getSales());
    ImageLoad imageLoad=new ImageLoad(my_activity,goodsBeanList.get(position).getPic(),viewHolder.pic);
        imageLoad.setImage();
        return convertView;
}
static class ViewHolder {
    ImageView pic;
    TextView textView_name;
    TextView textView_price;
    //TextView textView_sales;
}
}
