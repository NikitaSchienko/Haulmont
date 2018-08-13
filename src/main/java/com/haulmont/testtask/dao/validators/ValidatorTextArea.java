package com.haulmont.testtask.dao.validators;

import com.vaadin.data.Validator;

public class ValidatorTextArea implements Validator
{
    @Override
    public void validate(Object value) throws InvalidValueException
    {
        if (value.toString().length() == 0)
        {
            throw new Validator.InvalidValueException("Введите описание рецепта!");
        }if (value.toString().length() > 500)
        {
            throw new Validator.InvalidValueException("Описание рецепта превышает 500 символов");
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
