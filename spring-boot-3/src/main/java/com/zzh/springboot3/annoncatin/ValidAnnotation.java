package com.zzh.springboot3.annoncatin;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/18 17:35
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidAnnotationValidator.class)
//可以在同一个字段中使用多个相同的注解
@Repeatable(ValidAnnotation.List.class)
public @interface ValidAnnotation {

    String[] value() default {};

    String message() default "flag is not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ValidAnnotation[] value();
    }

}
