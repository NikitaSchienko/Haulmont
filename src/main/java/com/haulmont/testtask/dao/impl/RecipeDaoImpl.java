package com.haulmont.testtask.dao.impl;

import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.dao.database.Database;
import com.haulmont.testtask.decorator.Decorator;
import com.haulmont.testtask.pojo.Doctor;
import com.haulmont.testtask.pojo.Patient;
import com.haulmont.testtask.pojo.Recipe;
import com.haulmont.testtask.decorator.RecipeDecorator;
import com.haulmont.testtask.decorator.Record;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDaoImpl implements RecipeDao
{
    @Override
    public List<Record> findAll() throws SQLException {

        List<Record> recipes = new ArrayList<Record>();

        Connection connection = null;
        Statement statement = null;

        String selectSql = "SELECT ID, DESCRIPTION, PATIENT, DOCTOR, DATE_START, DATE_LENGTH, PRIORITY FROM RECIPE";

        try
        {
            connection = Database.getInstance().getConnection();
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(selectSql);

            while (rs.next())
            {
                RecipeDecorator recipe = new RecipeDecorator(new Recipe(
                        rs.getLong("ID"),
                        rs.getString("DESCRIPTION"),
                        rs.getLong("PATIENT"),
                        rs.getLong("DOCTOR"),
                        rs.getLong("DATE_START"),
                        rs.getLong("DATE_LENGTH"),
                        rs.getString("PRIORITY")
                ));

                recipes.add(recipe);
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
            if (connection != null) {
                connection.close();
            }
        }
        return recipes;
    }

    @Override
    public Record findRecipe(Long id)
    {
        return null;
    }

    @Override
    public void insert(Record recipe) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSql = "INSERT INTO RECIPE(DESCRIPTION, PATIENT, DOCTOR, DATE_START, DATE_LENGTH, PRIORITY) VALUES (?,?,?,?,?,?)";
        System.out.println((Long) recipe.getDateEnd());
        try
        {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(insertSql);

            preparedStatement.setString(1,recipe.getDescription());
            preparedStatement.setLong(2,(Long) recipe.getPatient());
            preparedStatement.setLong(3,(Long)recipe.getDoctor());
            preparedStatement.setLong(4,(Long)recipe.getDateStart());
            preparedStatement.setLong(5,(Long) recipe.getDateEnd());
            preparedStatement.setString(6,recipe.getPriority());

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
    public void update(long id, Record recipe) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSql = "UPDATE RECIPE SET DESCRIPTION = ? , DOCTOR = ? , PATIENT = ? , DATE_START = ? , DATE_LENGTH = ?, PRIORITY = ?" +
                "WHERE ID = ?";

        try
        {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(updateSql);

            Decorator decoratorRecipe = new Decorator(recipe);

            preparedStatement.setString(1, decoratorRecipe.getDescription());
            preparedStatement.setLong(2, ((Doctor)decoratorRecipe.getDoctor()).getId());
            preparedStatement.setLong(3, ((Patient)decoratorRecipe.getPatient()).getId());
            preparedStatement.setLong(4, (Long) decoratorRecipe.getDateStart());
            preparedStatement.setLong(5, (Long)decoratorRecipe.getDateEnd());
            preparedStatement.setString(6, decoratorRecipe.getPriority());
            preparedStatement.setLong(7, decoratorRecipe.getId());

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
    public void delete(long id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        String deleteSql = "DELETE FROM RECIPE WHERE ID = ?";

        try
        {
            connection = Database.getInstance().getConnection();
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
