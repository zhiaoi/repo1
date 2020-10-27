package com.briup.server.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DBUtil {
    
    public  static final boolean COMMIT = true;
    public  static final boolean UN_COMMIT = false;
    
    private static DataSource dataSource = null;
    static {
        Properties properties = new Properties();
        try {
            properties.load(DBUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.out.println("创建连接池发生异常:"+e.getMessage());
            System.exit(-1);
        }
    }
    
    public static  Connection getConnection(boolean autoCommit) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(autoCommit);
        return connection;
    }
    
    public static  Connection getConnection() throws Exception {
        return getConnection(true);
    }
    
    public  static void close(Connection connection,Statement statement,ResultSet rs) {
        try {
            if (connection != null) {
                connection.close();
            }
            
            if (statement != null) {
                statement.close();
            }
            
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
