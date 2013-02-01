package com.github.javarch.support.utils;

import java.util.regex.Pattern;

import org.junit.Test;

import com.github.javarch.support.Assert;

public class PasswordGeneratorTest {

	PasswordGenerator generator = new PasswordGenerator();
	
	@Test
	public void test() {
		//given
		String pwd_pattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*["+ PasswordGenerator.CARACTERES_ESPECIAIS+"])).{8,8}";
		Pattern pattern = Pattern.compile(pwd_pattern);
		
		//when
		String pw = generator.generate(8);
		
		//then
		Assert.isTrue( pattern.matcher(pw).matches() );
	}
}
