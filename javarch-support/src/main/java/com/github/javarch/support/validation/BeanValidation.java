package com.github.javarch.support.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

/**
 * http://stackoverflow.com/questions/7080684/spring-validator-having-both-annotation-and-validator-implementation
 * 
 *
 */
public abstract class BeanValidation implements org.springframework.validation.Validator {
	
	
	@Autowired
	private Validator validator;
	
	private boolean useValidationsByAnnotations;
	
	public BeanValidation() {
		super();
		this.useValidationsByAnnotations = true;
	}
	
	public BeanValidation(boolean useValidationsByAnnotations) {
		super();
		this.useValidationsByAnnotations = useValidationsByAnnotations;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {

		Assert.notNull(target, "Não é possivel aplicar regras de validação JSR-303 em um objeto nulo.");
		
		if ( useValidationsByAnnotations ){
			Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
			
		    for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
		        String propertyPath = constraintViolation.getPropertyPath().toString();
		        String message = constraintViolation.getMessage();
		        errors.rejectValue(propertyPath, "", message);
		    }
		}
	    
	    addExtraValidation(target, errors);
	}

	protected abstract void addExtraValidation(Object target, Errors errors);

}
