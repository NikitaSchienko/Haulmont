package com.haulmont.testtask.dao.impl;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.database.Database;
import com.haulmont.testtask.dao.pojo.Patient;

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

        String selectTableSQL = "SELECT p.id, p.name, p.surname, p.patronymic, p.phone  FROM patient p";
        try
        {
            dbConnection = Database.getConnection();
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
    public Patient findPatient(Long id) {
        return null;
    }

    @Override
    public void insert(Patient patient) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSql = "INSERT INTO patient(name, surname, patronymic, phone) VALUE(?,?,?,?)";

        try
        {
            connection = Database.getConnection();
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

        String updateSql = "UPDATE patient SET name = ? , surname = ? , patronymic = ? , phone = ? " +
                "WHERE id = ?";

        try
        {
            connection = Database.getConnection();
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
    public void delete(long id) throws SQLException
    {
        Connection connection = null;
        PreparedStatement statement = null;

        String deleteSql = "DELETE FROM haulmont.patient WHERE id = ?";

        try
        {
            connection = Database.getConnection();
            statement = connection.prepareStatement(deleteSql);
            statement.setLong(1,id);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
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
