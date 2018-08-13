package com.haulmont.testtask.dao.validators;

import com.vaadin.data.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorDate implements Validator
{
    @Override
    public void validate(Object value) throws InvalidValueException
    {
        if (value == null)
        {
            throw new Validator.InvalidValueException("Выберите дату!");
        }
    }

    public boolean isValid(Object value) {
        try {
            validate(value);
        } catch (Validator.InvalidValueException ive) {
            return false;
        }
        return true;
    }
}
