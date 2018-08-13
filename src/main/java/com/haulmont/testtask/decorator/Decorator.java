package com.haulmont.testtask.decorator;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Decorator implements Record
{
    protected Record record;


    public Decorator(Record record)
    {
        this.record = record;
    }


    @Override
    public Long getId() {
        return record.getId();
    }

    @Override
    public String getDescription() {
        return record.getDescription();
    }

    @Override
    public Object getPatient()
    {
        return this.record.getPatient();
    }

    @Override
    public Object getDoctor() {
        return this.record.getDoctor();
    }


    @Override
    public Object getDateEnd() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            String s = (String) this.record.getDateEnd();
            Date date = dateFormat.parse(s);
            Long l = date.getTime();

            return l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPriority() {
        return record.getPriority();
    }

    @Override
    public Object getDateStart() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            String s = (String) this.record.getDateStart();
            Date date = dateFormat.parse(s);
            Long l = date.getTime();

            return l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setDescription(String description) {
        this.record.setDescription(description);
    }

    @Override
    public void setId(Long id) {
        this.record.setId(id);
    }

    @Override
    public void setPatient(Object patient) {
        this.record.setPatient(patient);
    }

    @Override
    public void setDoctor(Object doctor) {
        this.record.setDoctor(doctor);
    }

    @Override
    public void setDateEnd(Object dateEnd) {
        this.record.setDateEnd(dateEnd);
    }

    @Override
    public void setPriority(String priority) {
        this.record.setPriority(priority);
    }

    @Override
    public void setDateStart(Object dateStart) {
        this.record.setDateStart(dateStart);
    }
}
