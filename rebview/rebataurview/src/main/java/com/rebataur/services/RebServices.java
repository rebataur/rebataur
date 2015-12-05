/*
 * Copyright 2015 ranjan.
 *
 *MPL
 */
package com.rebataur.services;

import com.rebataur.WicketApplication;
import com.rebataur.utils.AWSPGConfig;
import com.rebataur.utils.Constants;
import com.rebataur.utils.ConstantsSQL;
import com.rebataur.utils.DBConn;
import com.rebataur.utils.RebConfig;
import com.rebataur.utils.TwitterConfig;
import com.rebataur.utils.WeatherConfig;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ranjan
 */
public class RebServices {

    private static Connection sqliteConn;
    private static Connection pgConn;

    public RebServices() {

    }

    public RebConfig getRebConfig() throws ClassNotFoundException, SQLException {
        String[] pgConn = DBConn.getInstance().getPGConnString();

        RebConfig rebConfig = new RebConfig();
        rebConfig.setHostname(pgConn[0]);
        rebConfig.setPort(Integer.parseInt(pgConn[1]));
        rebConfig.setDatabase(pgConn[2]);
        rebConfig.setUsername(pgConn[3]);
        rebConfig.setPassword(pgConn[4]);

        return rebConfig;
    }

    public TwitterConfig getTwitterConfig() throws ClassNotFoundException, SQLException {
        String sql = "select key from service_reg where service_name ='twitter_srv' and key_name = '%s'";

        String[] twitterKey = {"access_token", "access_token_secret", "consumer_key", "consumer_secret"};
        TwitterConfig twitterConfig = new TwitterConfig();
        System.out.println(String.format(sql, twitterKey[0]));
        ResultSet rs = selectSQL(String.format(sql, twitterKey[0]));
        while (rs.next()) {
            twitterConfig.setAccessToken(rs.getString("key"));
        }
        rs = selectSQL(String.format(sql, twitterKey[1]));
        while (rs.next()) {
            twitterConfig.setAccessTokenSecret(rs.getString("key"));
        }
        rs = selectSQL(String.format(sql, twitterKey[2]));
        while (rs.next()) {
            twitterConfig.setConsumerKey(rs.getString("key"));
        }
        rs = selectSQL(String.format(sql, twitterKey[3]));
        while (rs.next()) {
            twitterConfig.setConsumerSecret(rs.getString("key"));
        }

        return twitterConfig;

    }

    public void saveAndInitRebConfig(RebConfig rebConfig) throws SQLException {
        Connection conn = getSqliteConn();
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("delete from config");
        String s = "insert into config(key,value) values('%s','%s')";
        String sql = String.format(s, "hostname", rebConfig.getHostname());
        stmt.executeUpdate(sql);
        sql = String.format(s, "port", rebConfig.getPort());
        stmt.executeUpdate(sql);
        sql = String.format(s, "database", rebConfig.getDatabase());
        stmt.executeUpdate(sql);
        sql = String.format(s, "username", rebConfig.getUsername());
        stmt.executeUpdate(sql);
        sql = String.format(s, "password", rebConfig.getPassword());
        stmt.executeUpdate(sql);
        

    }

    public void saveAndInitTwitterConfig(TwitterConfig twitterConfig) throws SQLException {
        String sql = "delete from service_reg where service_name = 'twitter_srv'";
        executeSQL(sql);
        String[] twitterKey = {"access_token", "access_token_secret", "consumer_key", "consumer_secret"};
        sql = String.format("insert into service_reg(service_name,key_name,key, fk_id) values('%s','%s','%s',%s)", "twitter_srv", twitterKey[0], twitterConfig.getAccessToken(), 1);
        insertSQL(sql);
        sql = String.format("insert into service_reg(service_name,key_name,key, fk_id) values('%s','%s','%s',%s)", "twitter_srv", twitterKey[1], twitterConfig.getAccessTokenSecret(), 1);
        insertSQL(sql);
        sql = String.format("insert into service_reg(service_name,key_name,key, fk_id) values('%s','%s','%s',%s)", "twitter_srv", twitterKey[2], twitterConfig.getConsumerKey(), 1);
        insertSQL(sql);
        sql = String.format("insert into service_reg(service_name,key_name,key, fk_id) values('%s','%s','%s',%s)", "twitter_srv", twitterKey[3], twitterConfig.getConsumerSecret(), 1);
        insertSQL(sql);

        sql = String.format(ConstantsSQL.fdwTwitterSQL, twitterConfig.getAccessToken(), twitterConfig.getAccessTokenSecret(), twitterConfig.getConsumerKey(), twitterConfig.getConsumerSecret());
        executeSQL(sql);
        
        executeSQL(ConstantsSQL.fdwTwitterSQL);
    }

    public AWSPGConfig getAWSPGConfig()  {
        try {
            String s = "select key from service_reg where service_name = 'aws_pg_srv' and key_name = '%s'";
            AWSPGConfig aWSPGConfig = new AWSPGConfig();
            String sql = String.format(s,"aws_pg_hostname");
            System.out.println(sql);
            aWSPGConfig.setHostname( selectSingleSQL(sql,"key"));
            sql = String.format(s,"aws_pg_port");
            aWSPGConfig.setPort(Integer.parseInt(selectSingleSQL(sql,"key")));
            sql = String.format(s,"aws_pg_database");
            aWSPGConfig.setDatabase(selectSingleSQL(sql,"key"));
            sql = String.format(s,"aws_pg_username");
            aWSPGConfig.setUsername(selectSingleSQL(sql,"key"));
            sql = String.format(s,"aws_pg_password");
            aWSPGConfig.setPassword(selectSingleSQL(sql,"key"));
            return aWSPGConfig;
        } catch (SQLException ex) {
            Logger.getLogger(RebServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void saveAndInitAWSPGConfig(AWSPGConfig awsPGConfig) throws SQLException{
        executeSQL("delete from service_reg where service_name = 'aws_pg_srv'");
        String s = "insert into service_reg(service_name,key_name,key, fk_id) values('%s','%s','%s',%s)";
        
        String sql = String.format(s, "aws_pg_srv", "aws_pg_hostname", awsPGConfig.getHostname(), 2);
        insertSQL(sql);
        sql = String.format(s, "aws_pg_srv", "aws_pg_port", awsPGConfig.getPort(), 2);
        insertSQL(sql);
        sql = String.format(s, "aws_pg_srv","aws_pg_database", awsPGConfig.getDatabase(),2);
        insertSQL(sql);
        sql = String.format(s, "aws_pg_srv","aws_pg_username", awsPGConfig.getUsername(),2);
        insertSQL(sql);
        sql = String.format(s, "aws_pg_srv","aws_pg_password", awsPGConfig.getPassword(),2);
        insertSQL(sql);
        
    }

    public WeatherConfig getWeatherConfig() throws SQLException {
        String sql = "select key from service_reg where service_name ='openweathermap_srv' and key_name = 'key'";

        WeatherConfig weatherConfig = new WeatherConfig();
        ResultSet rs = selectSQL(sql);
        while (rs.next()) {
            weatherConfig.setKey(rs.getString("key") == null ? "" : rs.getString("key"));
        }
        return weatherConfig;
    }

    public void saveAndInitWeatherConfig(WeatherConfig weatherConfig) throws SQLException {
        String sql = "delete from service_reg where service_name = 'openweathermap_srv'";
        executeSQL(sql);
        sql = String.format("insert into service_reg(service_name,key_name,key, fk_id) values('%s','%s','%s',%s)", "openweathermap_srv", "key", weatherConfig.getKey(), 3);
        insertSQL(sql);
        executeSQL(ConstantsSQL.fdwWeatherSQL);
        

    }

    public boolean testPGConn() {
        boolean pgConnTest = false;
        try {
            DBConn.getInstance().getConnection(Constants.CONN_TYPE.POSTGRES);
            pgConnTest = true;
            initRebataurDB();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(WicketApplication.class.getName()).log(Level.SEVERE, null, ex);
            pgConnTest = false;
        }
        return pgConnTest;
    }

    public static Connection getSqliteConn() throws SQLException {
        if (sqliteConn == null) {
            sqliteConn = getDBConn(Constants.CONN_TYPE.SQLITE);
        }
        return sqliteConn;
    }

    public static Connection getPGConn() throws SQLException {
        if (pgConn == null) {
            pgConn = getDBConn(Constants.CONN_TYPE.POSTGRES);
        }
        return pgConn;
    }

    public static Connection getDBConn(Constants.CONN_TYPE connType) throws SQLException {
        try {
            return DBConn.getInstance().getConnection(connType);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WicketApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void initRebataurDB() throws SQLException {

        executeSQL(ConstantsSQL.initPGSQL);
        executeSQL(ConstantsSQL.createMetaTables);
        System.out.println("**********************************");
//        System.out.println(selectSingleSQL("select weather_data from fdw_openweathermap where city_name = 'london' and country_name = 'india'", "weather_data"));

    }

    private void executeSQL(String sql) throws SQLException {
        Connection conn = getPGConn();
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    private ResultSet selectSQL(String sql) throws SQLException {
        Connection conn = getPGConn();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    private String selectSingleSQL(String sql, String key) throws SQLException {
        Connection conn = getPGConn();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            return rs.getString(key);
        }
        return "";
    }

    private void insertSQL(String sql) throws SQLException {
        Connection conn = getPGConn();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    public boolean testAWSPGConn() {
          boolean isConnected = false;
        try {
          
            
            DBConn.getAWSPGConn(getAWSPGConfig());
            isConnected = true;
            executeSQL(ConstantsSQL.fdwAWSPG);
        } catch (ClassNotFoundException | SQLException  ex) {
            Logger.getLogger(RebServices.class.getName()).log(Level.SEVERE, null, ex);
            isConnected= false;
        } catch(Exception ex){
             Logger.getLogger(RebServices.class.getName()).log(Level.SEVERE, null, ex);
            isConnected= false;
        }
        return isConnected;
               
    }

}
