package com.haulmont.testtask.dao.database;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database
{

    public static Connection getConnection()  {

//        InitialContext initContext= null;
//        Database database = null;
//
//        try {
//            initContext = new InitialContext();
//            database = (Database) initContext.lookup("java:comp/env/jdbc/appname");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//
//        return database.getConnection();

        Connection dbConnection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.print("ERROR!");
        }
        String url = "jdbc:mysql://localhost:3306/haulmont";
        String username = "root";
        String password = "root";
        try {
            dbConnection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.print("ERROR!");
        }
        return dbConnection;    }

}
