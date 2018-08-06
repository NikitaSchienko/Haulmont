package com.haulmont.testtask.dao.pojo;

import com.haulmont.testtask.decorator.Record;

public class Recipe implements Record
{
    private Long id;
    private String description;
    private Long patient;
    private Long doctor;
    private Long dateStart;
    private Long dateEnd;
    private String priority;

    public Recipe(Long id, String description, Long patient, Long doctor, Long dateStart, Long dateEnd, String priority) {
        this.id = id;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.priority = priority;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Long getPatient()
    {
        return patient;
    }

    public void setPatient(Long patient)
    {
        this.patient = patient;
    }

    public Long getDoctor()
    {
        return doctor;
    }

    public void setDoctor(Long doctor)
    {
        this.doctor = doctor;
    }

    public Long getDateStart()
    {
        return dateStart;
    }

    public void setDateStart(Long dateStart)
    {
        this.dateStart = dateStart;
    }

    public Object getDateEnd()
    {
        return dateEnd;
    }

    public void setDateEnd(Long dateEnd)
    {
        this.dateEnd = dateEnd;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }
}
