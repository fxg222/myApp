package com.example.fxg.ggg.bean;

import android.os.Parcel;
import android.os.Parcelable;
//
public class GoodsBean implements Parcelable{
    private String pic;
    private String name;
    private String sales;
    private String price;


    public GoodsBean(Parcel in) {
        pic=in.readString();
        name = in.readString();
        sales = in.readString();
        price=in.readString();
    }

    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel in) {
            return new GoodsBean(in);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };

    public GoodsBean() {

    }
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pic);
        dest.writeString(name);
        dest.writeString(sales);
        dest.writeString(price);
    }
}
