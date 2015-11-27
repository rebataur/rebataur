/*
 * Copyright 2015 ranjan.
 *
 * MPL
 */
package com.rebataur.utils;

/**
 *
 * @author ranjan
 */
public class DBConn {
    
    private DBConn() {
    }
    
    public static DBConn getInstance() {
        return DBConnHolder.INSTANCE;
    }
    
    private static class DBConnHolder {

        private static final DBConn INSTANCE = new DBConn();
    }
}
