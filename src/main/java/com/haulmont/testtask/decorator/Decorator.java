package com.haulmont.testtask.decorator;

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
    public Long getPatient() {
        return record.getPatient();
    }

    @Override
    public Long getDoctor() {
        return record.getDoctor();
    }


    @Override
    public Object getDateEnd() {
        return record.getDateEnd();
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
}
