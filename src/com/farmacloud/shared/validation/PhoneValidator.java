package com.farmacloud.shared.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@NotBlank
@Digits(integer=9, fraction=0,message="Por favor, introduzca un numero de telefono correcto")
@Size(min=9, max=9, message="Por favor, introduzca un numero de telefono correcto")
@Target( {ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface PhoneValidator {
    String message() default "Por favor, introduzca un numero de telefono correcto";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}