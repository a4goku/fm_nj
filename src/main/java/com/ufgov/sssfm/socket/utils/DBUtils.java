package com.ufgov.sssfm.socket.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.apache.log4j.Logger;

public class DBUtils {

    //private static Logger log = Logger.getLogger(DBUtils.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            //log.error(e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/zhpt", "root", "1234");
        //Connection conn = DriverManager.getConnection("jdbc:mysql://10.10.5.62:3308/springboot0801", "root", "root");
        //Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@10.10.5.62:1521:ORCL", "fm_dev0926", "1");
        return conn;
    }


    public static void close(Connection conn, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                //log.error(e.getMessage(), e);
            }
        }
    }
}
