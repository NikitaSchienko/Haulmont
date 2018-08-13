package com.haulmont.testtask.dao.impl;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.database.Database;
import com.haulmont.testtask.pojo.Patient;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao
{
    @Override
    public List<Patient> findAll() throws SQLException
    {
        List<Patient> patients = new ArrayList<Patient>();

        Connection dbConnection = null;
        Statement statement = null;

        String selectTableSQL = "SELECT ID, NAME ,SURNAME, SURNAME, PATRONYMIC, PHONE  FROM PATIENT";
        try
        {
            dbConnection = Database.getInstance().getConnection();
            statement = dbConnection.createStatement();

            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next())
            {
                Patient patient = new Patient(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        rs.getLong("phone")
                );

                patients.add(patient);
            }

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally {

            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return patients;
    }

    @Override
    public Patient findPatient(Long id) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;

        Patient patient = null;

        String selectTableSQL = "SELECT ID, NAME, SURNAME, PATRONYMIC, PHONE  FROM PATIENT WHERE ID = "+id;
        try
        {
            dbConnection = Database.getInstance().getConnection();
            statement = dbConnection.createStatement();

            ResultSet rs = statement.executeQuery(selectTableSQL);

            if (rs.next())
            {
                patient = new Patient(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        rs.getLong("phone")
                );

            }

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return patient;
    }

    @Override
    public void insert(Patient patient) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSql = "INSERT INTO PATIENT(NAME, SURNAME, PATRONYMIC, PHONE) VALUES(?,?,?,?)";

        try
        {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(insertSql);

            preparedStatement.setString(1,patient.getName());
            preparedStatement.setString(2,patient.getSurname());
            preparedStatement.setString(3,patient.getPatronymic());
            preparedStatement.setLong(4,patient.getPhone());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally {

            if (preparedStatement != null)
            {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }

        }
    }

    @Override
    public void update(long id, Patient patient) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSql = "UPDATE PATIENT SET NAME = ? , SURNAME = ? , PATRONYMIC = ? , PHONE = ? " +
                "WHERE ID = ?";

        try
        {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(updateSql);

            preparedStatement.setString(1, patient.getName());
            preparedStatement.setString(2, patient.getSurname());
            preparedStatement.setString(3, patient.getPatronymic());
            preparedStatement.setLong(4, patient.getPhone());
            preparedStatement.setLong(5, id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally {

            if (preparedStatement != null)
            {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }

        }

    }

    @Override
    public void delete(long id) throws Exception
    {
        Connection connection = null;
        PreparedStatement statement = null;

        String deleteSql = "DELETE FROM PATIENT WHERE ID = ?";

        try
        {
            connection = Database.getInstance().getConnection();
            statement = connection.prepareStatement(deleteSql);
            statement.setLong(1,id);

            statement.executeUpdate();
        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            throw new SQLIntegrityConstraintViolationException();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(statement != null)
            {
                statement.close();
            }
            if (connection != null)
            {
                connection.close();
            }
        }
    }
}
