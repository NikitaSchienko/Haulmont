package com.haulmont.testtask.decorator;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.impl.DoctorDaoImpl;
import com.haulmont.testtask.dao.impl.PatientDaoImpl;
import com.haulmont.testtask.pojo.Doctor;
import com.haulmont.testtask.pojo.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecipeDecorator extends Decorator
{

    public RecipeDecorator(Record record) {
        super(record);
    }

    public Long getId() {
        return record.getId();
    }


    public void setPatient(Object patient)
    {
        this.record.setPatient(((Patient)patient).getId());
    }

    public void setDoctor(Object doctor)
    {
        this.record.setDoctor(((Doctor)doctor).getId());
    }

    public void setDateStart(Object dateStart)
    {
        this.record.setDateStart(((Date)dateStart).getTime());
    }

    public void setDateEnd(Object dateEnd)
    {
        this.record.setDateEnd(((Date)dateEnd).getTime());
    }

    public String getPriority() {
        return record.getPriority();
    }

    public String getDateStart()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(new Date((Long) this.record.getDateStart()));
    }
    public String getDateEnd()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(new Date((Long) this.record.getDateEnd()));
    }
    public Object getDoctor()
    {
        DoctorDao doctorDao = new DoctorDaoImpl();
        Doctor doctor = null;
        try
        {
            doctor = doctorDao.findDoctor((Long) this.record.getDoctor());
            if(doctor == null)
            {
                throw new NullPointerException();
            }
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return doctor;
    }

    public Object getPatient()
    {
        PatientDao patientDao = new PatientDaoImpl();
        Patient patient = null;
        try
        {
            patient = patientDao.findPatient((Long) this.record.getPatient());
            if(patient == null)
            {
                throw new NullPointerException();
            }
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return patient;
    }
}
