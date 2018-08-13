package com.haulmont.testtask.dao;

import com.haulmont.testtask.decorator.RecipeDecorator;
import com.haulmont.testtask.pojo.Recipe;
import com.haulmont.testtask.decorator.Record;

import java.sql.SQLException;
import java.util.List;

public interface RecipeDao
{
    public List<Record> findAll() throws SQLException;
    public Record findRecipe(Long id);
    public void insert(Record recipe) throws SQLException;
    public void update(long id, Record recipe) throws SQLException;
    public void delete(long id) throws SQLException;
}
