package com.haulmont.testtask.dao;

import com.haulmont.testtask.pojo.Patient;

import java.sql.SQLException;
import java.util.List;

public interface PatientDao
{
    public List<Patient> findAll() throws SQLException;
    public Patient findPatient(Long id) throws SQLException;
    public void insert(Patient patient) throws SQLException;
    public void update(long id, Patient patient) throws SQLException;
    public void delete(long id) throws Exception;
}
