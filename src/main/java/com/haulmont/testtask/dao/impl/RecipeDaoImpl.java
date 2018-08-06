package com.haulmont.testtask.dao.impl;

import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.dao.database.Database;
import com.haulmont.testtask.dao.pojo.Patient;
import com.haulmont.testtask.dao.pojo.Recipe;
import com.haulmont.testtask.decorator.RecipeDecorator;
import com.haulmont.testtask.decorator.Record;

import javax.sql.DataSource;
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

        String selectSql = "SELECT r.id, r.description, r.patient, r.doctor, r.dateStart, r.dateLength, r.priority FROM haulmont.recipe r";

        try
        {
            connection = Database.getConnection();
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(selectSql);

            while (rs.next())
            {
                Record recipe = new RecipeDecorator(new Recipe(
                        rs.getLong("id"),
                        rs.getString("description"),
                        rs.getLong("patient"),
                        rs.getLong("doctor"),
                        rs.getLong("dateStart"),
                        rs.getLong("dateLength"),
                        rs.getString("priority")
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
    public Recipe findRecipe(Long id)
    {
        return null;
    }

    @Override
    public void insert(Recipe recipe) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSql = "INSERT INTO recipe(description, patient, doctor, dateStart, dateLength, priority) VALUE(?,?,?,?,?,?)";

        try
        {
            connection = Database.getConnection();
            preparedStatement = connection.prepareStatement(insertSql);

            preparedStatement.setString(1,recipe.getDescription());
            preparedStatement.setLong(2,recipe.getPatient());
            preparedStatement.setLong(3,recipe.getDoctor());
            preparedStatement.setLong(4,recipe.getDateStart());
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
    public void update(long id, Recipe recipe) {

    }

    @Override
    public void delete(long id) {

    }
}
