package com.practice.news.Validation;

import com.practice.news.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Configurable
@EnableSpringConfigured
public class UniqueUserIdValidator implements ConstraintValidator<UniqueUserId, String> {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private UserService userService;

	@Override
	public void initialize(UniqueUserId constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) return true;
		entityManager.setFlushMode(FlushModeType.COMMIT);
		System.out.println("Value " + value);
		entityManager.setFlushMode(FlushModeType.AUTO);
		return !userService.findByUserid(value);
	}
}
