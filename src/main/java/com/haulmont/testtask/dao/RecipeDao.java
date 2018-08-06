package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.pojo.Recipe;
import com.haulmont.testtask.decorator.Record;

import java.sql.SQLException;
import java.util.List;

public interface RecipeDao
{
    public List<Record> findAll() throws SQLException;
    public Recipe findRecipe(Long id);
    public void insert(Recipe recipe) throws SQLException;
    public void update(long id, Recipe recipe) throws SQLException;
    public void delete(long id) throws SQLException;
}
