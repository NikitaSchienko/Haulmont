package com.haulmont.testtask.pojo;

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

    public void setPatient(Object patient)
    {
        this.patient = (Long) patient;
    }

    public Long getDoctor()
    {
        return doctor;
    }

    public void setDoctor(Object doctor)
    {
        this.doctor = (Long) doctor;
    }

    public Long getDateStart()
    {
        return dateStart;
    }

    public void setDateStart(Object dateStart)
    {
        this.dateStart = (Long) dateStart;
    }

    public Object getDateEnd()
    {
        return dateEnd;
    }

    public void setDateEnd(Object dateEnd)
    {
        this.dateEnd = (Long) dateEnd;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != null ? !id.equals(recipe.id) : recipe.id != null) return false;
        if (description != null ? !description.equals(recipe.description) : recipe.description != null) return false;
        if (patient != null ? !patient.equals(recipe.patient) : recipe.patient != null) return false;
        if (doctor != null ? !doctor.equals(recipe.doctor) : recipe.doctor != null) return false;
        if (dateStart != null ? !dateStart.equals(recipe.dateStart) : recipe.dateStart != null) return false;
        if (dateEnd != null ? !dateEnd.equals(recipe.dateEnd) : recipe.dateEnd != null) return false;
        return priority != null ? priority.equals(recipe.priority) : recipe.priority == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + patient.hashCode();
        result = 31 * result + doctor.hashCode();
        result = 31 * result + dateStart.hashCode();
        result = 31 * result + dateEnd.hashCode();
        result = 31 * result + priority.hashCode();
        return result;
    }
}
