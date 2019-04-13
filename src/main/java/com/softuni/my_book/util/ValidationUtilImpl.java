package com.softuni.my_book.util;


import com.softuni.my_book.util.contracts.ValidationUtils;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidationUtilImpl implements ValidationUtils {
    private Validator validator;

    public ValidationUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E object) {
        return this.validator.validate(object).size() == 0;
    }

}
