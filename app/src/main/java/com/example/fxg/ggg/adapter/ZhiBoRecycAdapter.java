package com.example.fxg.ggg.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fxg.ggg.ImageLoad2;
import com.example.fxg.ggg.R;
import com.example.fxg.ggg.bean.UserBean;

import java.util.List;

public class ZhiBoRecycAdapter extends RecyclerView.Adapter<ZhiBoRecycAdapter.ZhiboViewHolder>{
    List<UserBean> userBeanList;
    private Context context;
    /**
     * adapter 中item点击事件的监听对象.
     */
    private ZhiBoRecycAdapter.OnClickListener onClickListener;

    public ZhiBoRecycAdapter.OnClickListener getOnClickListener() {
        return onClickListener;
    }
    public void setOnClickListener(ZhiBoRecycAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ZhiBoRecycAdapter(Context context,List<UserBean>userBeanList){
        this.userBeanList=userBeanList;
        this.context=context;
    }

    @NonNull
    @Override
    public ZhiBoRecycAdapter.ZhiboViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ZhiBoRecycAdapter.ZhiboViewHolder holder = new ZhiBoRecycAdapter.ZhiboViewHolder(LayoutInflater.from(context).inflate(R.layout.zhibo_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ZhiBoRecycAdapter.ZhiboViewHolder myViewHolder, int i) {
        if(userBeanList.size()==0){
            TextView userId=myViewHolder.userId;
            userId.setText("暂没人直播");
        }
        else {


        final View itemView=myViewHolder.itemView;
//        ImageView imageView=myViewHolder.imageView;
//        TextView video_text=myViewHolder.video_text;
//        TextView video_user_name=myViewHolder.video_user_name;
        TextView userId=myViewHolder.userId;
        userId.setText("直播中："+userBeanList.get(i).getUsrName());
//        ImageLoad2 imageLoad=new ImageLoad2(context,userBeanList.get(i).getVideoPicUrl(),imageView);
//        imageLoad.setImage();
//        video_text.setText(userBeanList.get(i).getVideoText());
//        video_user_name.setText(userBeanList.get(i).getVideoUserName());
        userId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置单击事件并回调给页面
                if (onClickListener != null) {
                    onClickListener.onClick(itemView, myViewHolder.getLayoutPosition());
                }
            }

        });
        }
    }

    @Override
    public int getItemCount() {
        if(userBeanList.size()==0)
        {
            return 1;
        }
        return userBeanList.size();
    }

    public class ZhiboViewHolder extends RecyclerView.ViewHolder{

//        ImageView imageView;
////        TextView video_text;
////        TextView video_user_name;
        TextView userId;

        public ZhiboViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView=itemView.findViewById(R.id.imageView_url);
//            video_text=(TextView) itemView.findViewById(R.id.video_text);
//            video_user_name=itemView.findViewById(R.id.video_user_name);
            userId=itemView.findViewById(R.id.zhibo_user);
        }
    }

    public interface OnClickListener {
        /**
         * 点击事件.
         */
        void onClick(View itemView, int position);

        /**
         * 长点击事件.
         */
        void onLongClick(View itemView, int position);
    }
}
