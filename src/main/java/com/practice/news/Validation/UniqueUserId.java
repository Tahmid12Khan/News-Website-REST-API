package com.practice.news.Validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueUserIdValidator.class)
@Documented
public @interface UniqueUserId {
	String message() default "UserId Already Exists";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};


}
