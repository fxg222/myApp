package com.example.fxg.ggg;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteOpenHelper;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextView tv;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button ib5 = super.findViewById(R.id.button_login_submit);
        ib5.setOnClickListener(
                new View.OnClickListener()
                {    @Override
                public void onClick(View v)
                {

                    tv = (TextView) findViewById(R.id.textView_username);
                    String userid=tv.getText().toString();//获取输入的ID
                    tv1 = (TextView) findViewById(R.id.textView_userpassword);
                    String pass=tv1.getText().toString();//获取输入的密码

                    jdbc login=new jdbc();
                    int i=login.login(userid,pass);
                    if(i==1)
                    {
                        startActivity(new Intent(LoginActivity.this,IndexActivity.class));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"登录失败！",Toast.LENGTH_LONG).show();
                    }
                }
                }
        );
        Button ib6 = super.findViewById(R.id.button_zhuce_submit);
        ib6.setOnClickListener(
                new View.OnClickListener()
                {    @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(LoginActivity.this,ZhuceActivity.class));
                }
                }
        );
    }

}
