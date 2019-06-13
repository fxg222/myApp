package com.example.fxg.ggg;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB{
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
}
