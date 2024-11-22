package org.hexaware.util;

import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection connection = DbConnect.getDBConn();
        if (connection != null) {
            System.out.println("Test connection successful!");
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Test connection failed.");
        }
    }
}

