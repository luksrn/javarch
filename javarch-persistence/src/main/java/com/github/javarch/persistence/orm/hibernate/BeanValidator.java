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

package com.github.javarch.persistence.orm.hibernate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 * Validador das regras de classe de domínio através da JSR 303 (Hibernate Validator)
 * 
 * @author Lucas Oliveira
 *
 */
public class BeanValidator {
	
	
	/**
	 * Realiza a validação de um objeto através da JSR 303.
	 * 
	 * @param objeto Entidade que contém anotações da JSR 303
	 * 
	 * @return BindingResult Objeto que encapsula as mensagens de erros encontradas 
	 * nas validações do objeto através das anotações da JSR 303
	 */
	public static <T> BindingResult validate(T objeto) {
		
		Assert.notNull(objeto, "Não é possivel aplicar regras de validação JSR-303 em um objeto nulo.");
		
		BindingResult bindingResult = new BeanPropertyBindingResult(objeto, objeto.getClass().getSimpleName() );
		
		javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(objeto);
		
		for (ConstraintViolation<T> constraint : constraintViolations) {
			bindingResult.rejectValue(constraint.getPropertyPath().toString(), "", constraint.getMessage());
		}
		return bindingResult;
	}
	
}
