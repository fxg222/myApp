package com.example.fxg.ggg;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ZhuceActivity extends AppCompatActivity {

    private TextView tv;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    int a=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        //Toast.makeText(getApplicationContext(),"start",Toast.LENGTH_LONG).show();
        Button ib5 = findViewById(R.id.button_zhuce);

        ib5.setOnClickListener(new View.OnClickListener()
                               {
                                   @Override
                                   public void onClick(View v)
                                   {
                                       Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
                                       tv = (TextView) findViewById(R.id.textView_zhucename);
                                       String userid = tv.getText().toString();
                                       tv1 = (TextView) findViewById(R.id.textView_zhucepassword);
                                       String pass = tv1.getText().toString();
                                       tv2 = (TextView) findViewById(R.id.textView_zhucepassword2);
                                       String pass2 = tv2.getText().toString();
                                       tv3 = (TextView) findViewById(R.id.textView_nicheng);
                                       String nicheng = tv3.getText().toString();

                                       jdbc zhuce=new jdbc();
                                       int i=zhuce.zhuce(userid,pass,pass2,nicheng);

                                       if(i==0)
                                       {
                                           startActivity(new Intent(ZhuceActivity.this,LoginActivity.class));
                                           Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_LONG).show();
                                       }
                                       else
                                       {
                                           Toast.makeText(getApplicationContext(),"注册失败！",Toast.LENGTH_LONG).show();
                                       }
                                   }
                               }
        );
    }

}
