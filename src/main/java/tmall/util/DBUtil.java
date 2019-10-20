package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.*;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 15:58
 * DBUTIL:数据库工具类，这个类的作用是初始化驱动，并且提供一个getConnection用于获取链接。
 */
public class DBUtil {

    static String ip = "127.0.0.1";
    static int port = 3306;
    static String database = "tmall";
    static String encoding = "UTF-8";
    static String loginName = "root";
    static String password = "123456";

    static {
        //1、加载并注册数据库驱动
        try {
            forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
             conn  = DriverManager.getConnection("jdbc:mysql://localhost:3306/tmall?useSSL=false&serverTimezone=UTC","root","123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  conn;
    }

    public static void main(String[] args) {
        System.out.println(DBUtil.getConnection());
    }





}
