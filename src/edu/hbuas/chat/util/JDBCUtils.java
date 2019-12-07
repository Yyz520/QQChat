package edu.hbuas.chat.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
    /**
     * 获取连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{
        // 加载配置文件
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);

        // 获取连接参数
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driver");

        // 加载驱动
        Class.forName(driverClass);
        Connection conn = DriverManager.getConnection(url,user,password);
        return conn;
    }

    /**
     * 关闭资源
     * @param conn
     * @param ps
     */
    public static void closeResource(Connection conn, Statement ps){
        try{
            if(ps!=null){
                ps.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            if(conn!=null){
                conn.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
