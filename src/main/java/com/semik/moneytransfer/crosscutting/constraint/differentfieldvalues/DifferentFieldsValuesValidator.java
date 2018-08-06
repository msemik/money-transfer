package com.semik.moneytransfer.crosscutting.constraint.differentfieldvalues;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class DifferentFieldsValuesValidator implements ConstraintValidator<DifferentFieldValues, Object> {
    private DifferentFieldValues annotation;

    @Override
    public void initialize(DifferentFieldValues constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            if (StringUtils.isBlank(annotation.message())) {
                context.buildConstraintViolationWithTemplate("Value of field " + annotation.firstFieldName() + " must be different than " + annotation.secondFieldName());
            }
            Object firstFieldValue = FieldUtils.readField(value, annotation.firstFieldName(), true);
            Object secondFieldValue = FieldUtils.readField(value, annotation.secondFieldName(), true);
            return !Objects.equals(firstFieldValue, secondFieldValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
