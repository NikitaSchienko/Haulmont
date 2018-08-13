package com.haulmont.testtask.dao.validators;

import com.vaadin.data.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorSurname  implements Validator
{
    @Override
    public void validate(Object value) throws Validator.InvalidValueException {
        String pattern = "^[А-Яа-я]{1,15}$|^[А-Яа-я]{1,15}[ -][А-Яа-я]{1,15}$";

        Pattern r = Pattern.compile(pattern);

        if (value instanceof String)
        {
            Matcher m = r.matcher((String)value);
            if(!m.find())
            {
                throw new Validator.InvalidValueException("Ошибка ввода! Примеры ввода: Салтыков-Щедрин");
            }
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

