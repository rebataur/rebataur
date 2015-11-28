/*
 * Copyright 2015 ranjan.
 *
 * MPL
 */
package com.rebataur.utils;

import com.rebataur.WicketApplication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.stringtemplate.v4.ST;

/**
 *
 * @author ranjan
 */
public class DBConn {

    private static final String SQLITE_DRIVER_CLASS = "org.sqlite.JDBC";
    private static final String PG_DRIVER_CLASS = "org.postgresql.Driver";

    private DBConn() {
    }

    public static DBConn getInstance() throws ClassNotFoundException {
        return DBConnHolder.INSTANCE;
    }

    public Connection getConnection(Constants.CONN_TYPE connType) throws ClassNotFoundException, SQLException {
        if(connType == Constants.CONN_TYPE.POSTGRES){
            return getPGConn();
        
        }else if(connType == Constants.CONN_TYPE.SQLITE){
            return getSqliteConn();
        }
        return null;
    }

    private static class DBConnHolder {

        private static final DBConn INSTANCE = new DBConn();

    }

    public static Connection getSqliteConn() throws ClassNotFoundException, SQLException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName(SQLITE_DRIVER_CLASS);

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:rebconfig.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate(" create table if not exists config(name string,key string,value string) ");

            return connection;
        } catch (SQLException e) {
            throw e;
        } 
    }

    public static Connection getPGConn() throws ClassNotFoundException, SQLException {

        try {

            Class.forName(PG_DRIVER_CLASS);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }

        Connection connection = null;

        try {
            String[] pgConnVal = getPGConnString();
            String url = String.format("jdbc:postgresql://%s:%s/%s",pgConnVal[0],pgConnVal[1],pgConnVal[2]);
            
            connection = DriverManager.getConnection(
                    url, pgConnVal[3],pgConnVal[4]);

            return connection;
        } catch (SQLException e) {
            throw e;

        }

    }

    public static String[] getPGConnString() throws SQLException, ClassNotFoundException {
        String[] pgConfigKey = {"hostname", "port", "database", "username", "password"};
        String[] pgKeyVal = {"","0","","",""};
      

        Connection conn = getSqliteConn();
        Statement statement = conn.createStatement();
        for (int i = 0; i < pgConfigKey.length; i++) {
            ST sql = new ST("select value from config where key = '<value>'");
            sql.add("value", pgConfigKey[i]);
            
            ResultSet rs = statement.executeQuery(sql.render());
            while (rs.next()) {
                    pgKeyVal[i] = rs.getString("value");
            }

        }
        
        

        return pgKeyVal;
    }
}
