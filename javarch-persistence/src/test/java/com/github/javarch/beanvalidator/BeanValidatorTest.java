package com.github.javarch.beanvalidator;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import com.github.javarch.domain.User;
import com.github.javarch.persistence.orm.hibernate.BeanValidator;

/**
 * Testes unitários do componente de validação JSR 303
 * 
 * @author lucas
 *
 */
public class BeanValidatorTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void executaUmTeste(){
		BeanValidator.validate(null);
	}

	@Test
	public void verificaValidacoes(){
		BindingResult result = BeanValidator.validate( new User());
		 
		Assert.assertNotNull(result);
		Assert.assertTrue( result.hasErrors() );
	//	Assert.assertTrue( result.getFieldErrorCount() == 2);		
	}
	
}
