package com.haulmont.testtask.dao.impl;

import com.haulmont.testtask.dao.database.Database;
import com.haulmont.testtask.pojo.Statistic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StatisticDaoImpl
{
    public List<Statistic> findAll() throws Exception
    {
        List<Statistic> statistics = new ArrayList<Statistic>();

        Connection dbConnection = null;
        Statement statement = null;

        String selectTableSQL = "Select NAME, d.surname, d.patronymic, d.specialization, COUNT(r.id) as count from doctor d, recipe r where r.doctor = d.id group by d.id;";
        try
        {
            dbConnection = Database.getInstance().getConnection();
            statement = dbConnection.createStatement();

            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next())
            {
                Statistic statistic = new Statistic(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        rs.getString("specialization"),
                        rs.getInt("count")
                );

                statistics.add(statistic);
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
        return statistics;
    }
}
