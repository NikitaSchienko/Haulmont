package com.haulmont.testtask.dao.impl;

import com.haulmont.testtask.dao.database.Database;
import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.pojo.Doctor;

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

        String selectTableSQL = "SELECT d.id, d.name ,d.surname, d.patronymic, d.specialization  FROM doctor d";
        try
        {
            dbConnection = Database.getConnection();
            statement = dbConnection.createStatement();

            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next())
            {
                Doctor doctor = new Doctor(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        rs.getString("specialization")
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
    public Doctor findDoctor(Long id) {
        return null;
    }

    @Override
    public void insert(Doctor doctor) throws SQLException
    {
        Connection connection = null;
        Statement statement = null;

        String insertSql = "INSERT INTO doctor(name,surname,patronymic,specialization) "
                + "VALUES(?,?,?,?)";

        try
        {
            connection = Database.getConnection();

            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(insertSql);

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

        String updateSql = "UPDATE haulmont.doctor SET doctor.name = ? , doctor.surname = ? , doctor.patronymic = ? , doctor.specialization = ? " +
                "WHERE doctor.id = ?";

        try
        {
            connection = Database.getConnection();
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
    public void delete(long id) throws SQLException
    {
        Connection connection = null;
        PreparedStatement statement = null;

        String deleteSql = "DELETE FROM haulmont.doctor where id = ?";

        try
        {
            connection = Database.getConnection();
            statement = connection.prepareStatement(deleteSql);
            statement.setLong(1,id);

            statement.executeUpdate();
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
