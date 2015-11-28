/*
 * Copyright 2015 ranjan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rebataur.services;

import com.rebataur.WicketApplication;
import com.rebataur.utils.Constants;
import com.rebataur.utils.DBConn;
import com.rebataur.utils.RebConfig;
import java.sql.Connection;
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

    public void saveRebConfig(RebConfig rebConfig) throws SQLException {
        Connection sqliteConn = getSqliteConn();
        Statement stmt = sqliteConn.createStatement();

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
    
   public boolean testPGConn(){
        boolean pgConnTest = false;
        try {
             DBConn.getInstance().getConnection(Constants.CONN_TYPE.POSTGRES);
             pgConnTest = true;
        } catch (ClassNotFoundException|SQLException ex) {
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
}
