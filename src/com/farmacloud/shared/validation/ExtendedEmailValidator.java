package com.farmacloud.shared.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@NotBlank
@Pattern(regexp=".+@.+\\..+", message="Por favor, introduzca un email correcto")
@Target( {ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ExtendedEmailValidator {
    String message() default "Por favor, introduzca un email correcto";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}