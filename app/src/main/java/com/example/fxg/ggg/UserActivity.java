package com.example.fxg.ggg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        String a="";
        jdbc relogin=new jdbc();
        int i=relogin.relogin();//验证登录信息
        if (i==1)//若无登陆信息则跳转至登录页面，有则跳转至用户中心页面
        {
            MD5Utils p=new MD5Utils();
            a=p.read_id();//读取文件中的登录信息
            TextView name=findViewById(R.id.textView_my_name);
            name.setText(a);
        }
        else
        {
            startActivity(new Intent(UserActivity.this,IndexActivity.class));
        }


        Button ib5 = super.findViewById(R.id.button_zhuxiao);
        ib5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MD5Utils user=new MD5Utils();
                        user.delete("/storage/emulated/0/Android/data.txt");
                        user.delete("/storage/emulated/0/Android/pass.txt");
                        startActivity(new Intent(UserActivity.this,IndexActivity.class));
                    }//注销
                }
        );
    }
}