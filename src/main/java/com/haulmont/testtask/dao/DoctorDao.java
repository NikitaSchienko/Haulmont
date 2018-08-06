package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.pojo.Doctor;

import java.sql.SQLException;
import java.util.List;

public interface DoctorDao
{
    public List<Doctor> findAll() throws SQLException;
    public Doctor findDoctor(Long id);
    public void insert(Doctor doctor) throws SQLException;
    public void update(long id, Doctor doctor) throws SQLException;
    public void delete(long id) throws SQLException;
}
