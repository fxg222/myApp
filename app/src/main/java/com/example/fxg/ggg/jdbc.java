package com.example.fxg.ggg;

import android.content.Intent;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class jdbc{
    private static String driver="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://39.108.181.154:3306/store";
    private static String user="root";
    private static String password="123456";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn;
        conn=DriverManager.getConnection(url,user,password);
        return conn;
    }

    public static void free(Connection con, PreparedStatement pstm,ResultSet rs) throws SQLException {
        if(rs!=null)rs.close();
        if(pstm!=null)pstm.close();
        if(con!=null)con.close();
    }

    public int relogin()
    {
        data set=new data();
        Relogin dBdao=new Relogin(set);
        dBdao.start();
        dBdao.run();

        //Toast.makeText(getApplicationContext(),a,Toast.LENGTH_LONG).show();
        try
        {
            dBdao.join();
            if(dBdao.i==1)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int login(String id,String password)
    {
        data set=new data();
        set.setid(id);
        set.setpass(password);
        Login dBdao=new Login(set);
        dBdao.start();
        dBdao.run();
        try
        {
            dBdao.join();
            if(dBdao.i==1)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int zhuce(String id,String password,String password1,String nicheng) {
        data set = new data();
        set.setid(id);
        set.setpass(password);
        set.setpass1(password1);
        set.setnicheng(nicheng);
        Zhuce dBdao = new Zhuce(set);
        dBdao.start();
        dBdao.run();
        int i=set.geti();
        return i;
    }
    public class Zhuce extends Thread {

        data get;
        data set = new data();

        public Zhuce(data set) {
            this.get = set;
        }

        public void run() {

            String userid = get.getid();
            String pass = get.getpass();
            String pass2 = get.getpass2();
            String nicheng=get.getnicheng();
            int i=0;

            if (pass2.equals(pass)) {
                if (pass != null) {
                    MD5Utils pw = new MD5Utils();
                    String np = pw.toMD5(pass);
                    //Toast.makeText(getApplicationContext(),np,Toast.LENGTH_LONG).show();
                    Connection conn = null;
                    String str = "INSERT INTO `user` (userName,userPassword,userNicheng) VALUES ('" + userid + "','" + np + "','" + nicheng + "')";
                    String str1 = "CREATE TABLE `" + userid + "` (`goods_ID` int(11) NOT NULL,`goods_data` varchar(255) DEFAULT NULL," +
                            "`goods_num` varchar(255),`goods_adress` varchar(255),`goods_phone` varchar(255),`goods_index` varchar(255) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
                    try {
                        conn = jdbc.getConnection();
                        PreparedStatement pstm = conn.prepareStatement(str);
                        int rs = pstm.executeUpdate(str);//将注册信息写入用户表中
                        int rs1 = pstm.executeUpdate(str1);//建立用户表
                        //ResultSet rs1 = pstm.executeQuery();

                        if (rs == 0)
                        {
                            i=0;
                            set.seti(i);
                        }
                        else
                            {
                                i=1;
                                set.seti(i);
                            }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class Login extends Thread {

        data get;
        int i=0;

        public Login(data set) {
            this.get = set;
        }

        public void run() {

            String userid = get.getid();
            String pass = get.getpass();

            MD5Utils p=new MD5Utils();
            String pw="";
            String np=p.toMD5(pass);
            Connection conn = null;
            String str = "SELECT userPassword FROM user where userName = "+userid;

            try {
                conn = jdbc.getConnection();
                PreparedStatement pstm = conn.prepareStatement(str);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    pw = rs.getString("userPassword");//将从数据库中取得的密码赋值给pw
                }
            } catch (ClassNotFoundException e) { e.printStackTrace(); }
            catch (SQLException e) { e.printStackTrace(); }

            //Toast.makeText(getApplicationContext(),"正在登陆"+pw,Toast.LENGTH_LONG).show();
            if(np.equals(pw))//比对密码
            {
                i=1;
                MD5Utils user = new MD5Utils();
                user.writeData(userid);//登录成功，将用户ID写入文件中
                user.writePass(np);
            }
        }
    }

    public class Relogin extends Thread {
        data get;
        int i;

        public Relogin(data set) {
            this.get = set;
        }
        public void run() {
            MD5Utils p=new MD5Utils();
            p.write();//调用MD5Utils中的write函数创建存储登录信息的文件
            String a=p.read_id();//读取文件中的登录信息
            String pass=p.read_pass();//获取输入的密码
            String pw="";
            Connection conn = null;
            String str = "SELECT userPassword FROM user where userName = "+a;

            try {
                conn = jdbc.getConnection();
                PreparedStatement pstm = conn.prepareStatement(str);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    pw = rs.getString("userPassword");//将从数据库中取得的密码赋值给pw
                }
            } catch (ClassNotFoundException e) { e.printStackTrace(); }
            catch (SQLException e) { e.printStackTrace(); }

            //Toast.makeText(getApplicationContext(),"正在登陆"+pw,Toast.LENGTH_LONG).show();
            if(pw!=""&&pass.equals(pw))//比对密码
            {
                i=1;
            }
            else
            {
                i=0;
                /*p.delete("/storage/emulated/0/Android/data.txt");
                p.delete("/storage/emulated/0/Android/pass.txt");
                p.write();*/
            }
        }
    }
       /* public int login(String id,String password) {
            MD5Utils p=new MD5Utils();
            String userid=id;//获取输入的ID
            String pass=password;//获取输入的密码
            String pw="";
            String np=p.toMD5(pass);
            Connection conn = null;
            String str = "SELECT userPassword FROM user where userName = "+userid;

            try {
                conn = jdbc.getConnection();
                PreparedStatement pstm = conn.prepareStatement(str);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    pw = rs.getString("userPassword");//将从数据库中取得的密码赋值给pw
                }
            } catch (ClassNotFoundException e) { e.printStackTrace(); }
            catch (SQLException e) { e.printStackTrace(); }

            //Toast.makeText(getApplicationContext(),"正在登陆"+pw,Toast.LENGTH_LONG).show();
            if(np.equals(pw))//比对密码
            {
                MD5Utils user=new MD5Utils();
                user.writeData(userid);//登录成功，将用户ID写入文件中
                user.writePass(np);
                return 1;
            }
            else
            {
                return 0;
            }

        }

        public int relogin() {
            MD5Utils p=new MD5Utils();
            p.write();//调用MD5Utils中的write函数创建存储登录信息的文件
            String a=p.read_id();//读取文件中的登录信息
            String pass=p.read_pass();//获取输入的密码
            String pw="";
            String np=p.toMD5(pass);
            Connection conn = null;
            String str = "SELECT userPassword FROM user where userName = "+a;

            try {
                conn = jdbc.getConnection();
                PreparedStatement pstm = conn.prepareStatement(str);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    pw = rs.getString("userPassword");//将从数据库中取得的密码赋值给pw
                }
            } catch (ClassNotFoundException e) { e.printStackTrace(); }
            catch (SQLException e) { e.printStackTrace(); }

            //Toast.makeText(getApplicationContext(),"正在登陆"+pw,Toast.LENGTH_LONG).show();
            if(np.equals(pw))//比对密码
            {
                return 1;
            }
            else
            {
                return 0;
            }

        }*/
    }

