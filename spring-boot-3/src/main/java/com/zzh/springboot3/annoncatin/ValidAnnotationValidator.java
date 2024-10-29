package com.zzh.springboot3.annoncatin;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/18 17:36
 */
@Slf4j
public class ValidAnnotationValidator implements ConstraintValidator<ValidAnnotation, Object> {

    String[] values;

    @Override
    public void initialize(ValidAnnotation constraintAnnotation) {
        this.values = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }
        for (String v : values) {
            if (v.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
