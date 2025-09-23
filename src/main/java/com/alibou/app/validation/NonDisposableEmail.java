package com.alibou.app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//specify the scope and the target of our custom annotation
@Target({ElementType.FIELD, ElementType.PARAMETER}) //where we can use this annotation
@Retention(RetentionPolicy.RUNTIME) //where it's getting executed
@Constraint(validatedBy = EmailDomainValidator.class)
public @interface NonDisposableEmail { //use @interface keyword

    String message() default "Disposable email addresses are not allowed";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};

}
