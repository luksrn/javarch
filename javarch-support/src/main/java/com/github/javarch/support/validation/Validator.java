/*
* Copyright 2011 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License. You may obtain a copy of
* the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations under
* the License.
*/
package com.github.javarch.support.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

/**
 * http://stackoverflow.com/questions/7080684/spring-validator-having-both-annotation-and-validator-implementation
 * 
 *
 */
public abstract class Validator implements org.springframework.validation.Validator {
	
	
	@Autowired
	private javax.validation.Validator validator;
	
	private boolean useValidationsByAnnotations;
	
	public Validator() {
		super();
		this.useValidationsByAnnotations = true;
	}
	
	public Validator(boolean useValidationsByAnnotations) {
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
