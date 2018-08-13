package com.haulmont.testtask.dao.database;

import com.sun.org.apache.xpath.internal.SourceTree;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database
{

    private static Database instance;

    public static synchronized Database getInstance()
    {
        if (instance == null)
        {
            try
            {
                createTable();
                fillTables();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            instance = new Database();
        }
        return instance;
    }

    public static Connection getConnection()  {

        Connection dbConnection = null;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            System.err.print("Ошибка! org.hsqldb.jdbc.JDBCDriver - отсутствует!");
        }
        try {

            dbConnection = DriverManager.getConnection("jdbc:hsqldb:file:hsqldb", "SA", "");

        } catch (SQLException e) {
            System.err.print("Ошибка! Ошибка подключения к базе данных!!");
        }
        return dbConnection;
    }

    public static void createTable() throws SQLException {
        Connection connection = getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sqlTablePatient = "CREATE TABLE PATIENT(" +
                    "ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL PRIMARY KEY," +
                    "NAME VARCHAR(100) NOT NULL," +
                    "SURNAME VARCHAR(100) NOT NULL," +
                    "PATRONYMIC VARCHAR(100) NOT NULL," +
                    "PHONE BIGINT NOT NULL)";
            statement.executeUpdate(sqlTablePatient);

            String sqlTableDoctor = "CREATE TABLE DOCTOR(" +
                    "ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL PRIMARY KEY," +
                    "NAME VARCHAR(100) NOT NULL," +
                    "SURNAME VARCHAR(100) NOT NULL," +
                    "PATRONYMIC VARCHAR(100) NOT NULL," +
                    "SPECIALIZATION VARCHAR(100) NOT NULL" +
                    ")";
            statement.executeUpdate(sqlTableDoctor);
            String sqlTableRecipe = "CREATE TABLE RECIPE(" +
                    "ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL PRIMARY KEY," +
                    "DESCRIPTION VARCHAR(5000) NOT NULL," +
                    "PATIENT BIGINT NOT NULL," +
                    "DOCTOR BIGINT NOT NULL," +
                    "DATE_START BIGINT NOT NULL," +
                    "DATE_LENGTH BIGINT NOT NULL," +
                    "PRIORITY VARCHAR(100) NOT NULL," +
                    "FOREIGN KEY(DOCTOR) REFERENCES DOCTOR(ID)," +
                    "FOREIGN KEY(PATIENT) REFERENCES PATIENT(ID)" +
                    ")";

            statement.executeUpdate(sqlTableRecipe);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeConnection(connection);
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void fillTables() throws SQLException {
        Connection dbConnection = getConnection();
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();

            String sql = "INSERT INTO PATIENT VALUES(1,'Вадим','Дюжев','Александрович',89376562767)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO PATIENT VALUES(2,'Олег','Пахомов','Олегович',89376562315)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO PATIENT VALUES(3,'Елена','Белова','Александровна',89376562222)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO PATIENT VALUES(4,'Вадим','Иванов','Иванович',89371112267)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO PATIENT VALUES(5,'Кирилл','Сахипов','Видомов',89376552267)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO PATIENT VALUES(6,'Елена','Дружинина','Путина',89376562267)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO DOCTOR VALUES(1,'Вадим','Сидоров','Александрович','Окулист')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO DOCTOR VALUES(2,'Александр','Куц','Олегович','Лор')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO DOCTOR VALUES(3,'Ольга','Винина','Александровна','Терапевт')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO DOCTOR VALUES(4,'Вадим','Вадимов','Игоревич','Терапевт')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO RECIPE(DESCRIPTION, PATIENT, DOCTOR, DATE_START, DATE_LENGTH, PRIORITY) VALUES ('Аллергия на кошек. Зодак, Супрастин - принимать за 3 р/д',1,2,1535572800000,1535578800000,'Нормальный')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO RECIPE(DESCRIPTION, PATIENT, DOCTOR, DATE_START, DATE_LENGTH, PRIORITY) VALUES ('Противовирусное средство 3 раза в день в течении месяца',5,4,1535572800000,1535578800000,'Срочный')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO RECIPE(DESCRIPTION, PATIENT, DOCTOR, DATE_START, DATE_LENGTH, PRIORITY) VALUES ('Смекта по 1 табл. 1 раз в день',2,4,1535572800000,1599579500000,'Срочный')";
            statement.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            closeConnection(dbConnection);
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }


    private static void closeConnection(Connection connection) {

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "SHUTDOWN";
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
