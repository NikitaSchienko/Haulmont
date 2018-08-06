package com.haulmont.testtask.decorator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecipeDecorator extends Decorator
{

    public RecipeDecorator(Record record) {
        super(record);
    }

    public String getDateStart()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(new Date((Long) this.record.getDateStart()));
    }
}
