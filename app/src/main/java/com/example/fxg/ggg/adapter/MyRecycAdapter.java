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
import com.example.fxg.ggg.bean.VideoBean;

import java.util.List;

public class MyRecycAdapter extends RecyclerView.Adapter<MyRecycAdapter.MyViewHolder>{
        List<VideoBean>videoBeanList;
        private Context context;
        /**
         * adapter 中item点击事件的监听对象.
         */
        private OnClickListener onClickListener;

        public OnClickListener getOnClickListener() {
            return onClickListener;
        }
        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public MyRecycAdapter(Context context,List<VideoBean>videoBeanList){
            this.videoBeanList=videoBeanList;
            this.context=context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_item, viewGroup, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            final View itemView=myViewHolder.itemView;
            ImageView imageView=myViewHolder.imageView;
            TextView video_text=myViewHolder.video_text;
            TextView video_user_name=myViewHolder.video_user_name;

            ImageLoad2 imageLoad=new ImageLoad2(context,videoBeanList.get(i).getVideoPicUrl(),imageView);
            imageLoad.setImage();
            video_text.setText(videoBeanList.get(i).getVideoText());
            video_user_name.setText(videoBeanList.get(i).getVideoUserName());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 设置单击事件并回调给页面
                    if (onClickListener != null) {
                        onClickListener.onClick(itemView, myViewHolder.getLayoutPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return videoBeanList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView video_text;
            TextView video_user_name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.imageView_url);
                video_text=(TextView) itemView.findViewById(R.id.video_text);
                video_user_name=itemView.findViewById(R.id.video_user_name);
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
