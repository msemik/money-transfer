package com.semik.moneytransfer.crosscutting.constraint.differentfieldvalues;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = {DifferentFieldsValuesValidator.class})

public @interface DifferentFieldValues {
    String message() default "";

    String firstFieldName();

    String secondFieldName();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        DifferentFieldValues[] value();
    }

}
