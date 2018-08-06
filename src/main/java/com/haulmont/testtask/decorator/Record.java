package com.haulmont.testtask.decorator;

public interface Record
{

    public Long getId();
    public String getDescription();
    public Long getPatient();
    public Long getDoctor();
    public Object getDateEnd();
    public String getPriority();
    public Object getDateStart();
}
