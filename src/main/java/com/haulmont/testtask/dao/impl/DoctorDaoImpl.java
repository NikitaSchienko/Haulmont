package com.haulmont.testtask.dao.impl;

import com.haulmont.testtask.dao.database.Database;
import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.pojo.Doctor;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDaoImpl implements DoctorDao
{

    @Override
    public List<Doctor> findAll() throws SQLException
    {
        List<Doctor> doctors = new ArrayList<Doctor>();

        Connection dbConnection = null;
        Statement statement = null;

        String selectTableSQL = "SELECT ID, NAME ,SURNAME, SURNAME, PATRONYMIC, SPECIALIZATION  FROM DOCTOR";
        try
        {
            dbConnection = Database.getInstance().getConnection();
            statement = dbConnection.createStatement();

            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next())
            {
                Doctor doctor = new Doctor(
                        rs.getLong("ID"),
                        rs.getString("NAME"),
                        rs.getString("SURNAME"),
                        rs.getString("PATRONYMIC"),
                        rs.getString("SPECIALIZATION")
                );

                doctors.add(doctor);
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
        return doctors;
    }

    @Override
    public Doctor findDoctor(Long id) throws SQLException {

        Doctor doctor = null;

        Connection dbConnection = null;
        Statement statement = null;

        String selectTableSQL = "SELECT ID, NAME ,SURNAME, PATRONYMIC, SPECIALIZATION  FROM DOCTOR  WHERE ID = "+id;
        try
        {
            dbConnection = Database.getInstance().getConnection();
            statement = dbConnection.createStatement();

            ResultSet rs = statement.executeQuery(selectTableSQL);

            if(rs.next()) {
                doctor = new Doctor(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        rs.getString("specialization")
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
        return doctor;
    }

    @Override
    public void insert(Doctor doctor) throws SQLException
    {
        Connection connection = null;
        Statement statement = null;

        String insertSql = "INSERT INTO doctor(NAME,SURNAME,PATRONYMIC,SPECIALIZATION) "
                + "VALUES(?,?,?,?)";

        try
        {
            connection = Database.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

            preparedStatement.setString(1, doctor.getName());
            preparedStatement.setString(2, doctor.getSurname());
            preparedStatement.setString(3, doctor.getPatronymic());
            preparedStatement.setString(4, doctor.getSpecialization());

            preparedStatement.executeUpdate();

            System.out.println(insertSql);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());

        }
        finally
        {
            if (statement != null)
            {
                statement.close();
            }
            if (connection != null)
            {
                connection.close();
            }
        }

    }

    @Override
    public void update(long id, Doctor doctor) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSql = "UPDATE DOCTOR SET NAME = ? , SURNAME = ? , PATRONYMIC = ? , SPECIALIZATION = ? " +
                "WHERE ID = ?";

        try
        {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(updateSql);

            preparedStatement.setString(1, doctor.getName());
            preparedStatement.setString(2, doctor.getSurname());
            preparedStatement.setString(3, doctor.getPatronymic());
            preparedStatement.setString(4, doctor.getSpecialization());
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

        String deleteSql = "DELETE FROM DOCTOR where ID = ?";

        try
        {
            connection = Database.getConnection();
            statement = connection.prepareStatement(deleteSql);
            statement.setLong(1,id);


            statement.executeUpdate();

        }
        catch (SQLIntegrityConstraintViolationException e)
        {
            throw new SQLIntegrityConstraintViolationException();
        }
        catch (Exception e) {
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
