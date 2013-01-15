package com.github.javarch.support.utils;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.github.javarch.support.Assert;

/**
 * Gerador de senhas.
 * 
 * @author Lucas Oliveira
 *
 */
public class PasswordGenerator {
	
	public static final String LETRAS_MINUSCULA = "abcdefghijklmnopqrstuvwxyz";
	public static final String LETRAS_MAIUSCULA = LETRAS_MINUSCULA.toUpperCase();
	public static final String NUMEROS = "0123456789";
	public static final String CARACTERES_ESPECIAIS = "!@#$%&*()-:<>+/";

	private static final String [] FONTES = new String[] {
			LETRAS_MAIUSCULA, 
			LETRAS_MINUSCULA, 
			NUMEROS,
			CARACTERES_ESPECIAIS
	};
	
	public String generate(int size){
		Assert.isTrue(size > 0, "O tamanho da senha deve ser um valor > 0");				
		StringBuilder stringBuilder = new StringBuilder(size);
		for ( int i = 0, indexFonte = new Random().nextInt(FONTES.length + 1); i < size ; i++ ){
			indexFonte = i % FONTES.length;
			stringBuilder.append( RandomStringUtils.random(1, FONTES[indexFonte]) );						
		}
		return stringBuilder.toString();
	}
}
