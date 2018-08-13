package com.haulmont.testtask.decorator;

public interface Record
{

    public Long getId();
    public String getDescription();
    public Object getPatient();
    public Object getDoctor();
    public Object getDateEnd();
    public String getPriority();
    public Object getDateStart();
    public void setDescription(String description);
    public void setId(Long id);
    public void setPatient(Object patient);
    public void setDoctor(Object doctor);
    public void setDateEnd(Object dateEnd);
    public void setPriority(String priority);
    public void setDateStart(Object dateStart);
}
