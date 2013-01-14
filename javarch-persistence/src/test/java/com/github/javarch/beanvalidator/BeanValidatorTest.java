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
		// given
		User user = new User();

		//when
		BindingResult result = BeanValidator.validate( user );
		
		//then
		Assert.assertNotNull(result);
		Assert.assertTrue( result.hasErrors() );
		Assert.assertTrue( result.getFieldErrorCount() == 3);		
	}
	
	@Test
	public void verificaValidacoesEmail(){
		// given
		User user = new User();

		//when
		user.setNomeUsuario("luksrn");
		user.setEmail("luksrngmail.com");
		user.setSenha("2343455");
		BindingResult result = BeanValidator.validate( user );
		
		//then
		Assert.assertNotNull(result);
		Assert.assertTrue( result.hasErrors() );
		Assert.assertTrue( result.getFieldErrorCount() == 1);		
	}
	
	@Test
	public void verificaValidacoesEmailInvalido(){
		// given
		User user = new User();

		//when
		user.setEmail("luksrngmail.com");
		BindingResult result = BeanValidator.validate( user );
		
		//then
		Assert.assertNotNull(result);
		Assert.assertTrue( result.hasErrors() );
		Assert.assertTrue( result.getFieldErrorCount() == 3);		
	}
		
}
