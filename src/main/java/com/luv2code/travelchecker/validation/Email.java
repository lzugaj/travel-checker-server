package com.luv2code.travelchecker.validation;

import com.luv2code.travelchecker.validation.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.TYPE_USE
})
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
