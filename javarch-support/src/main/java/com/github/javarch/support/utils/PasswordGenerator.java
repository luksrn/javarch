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

package com.github.javarch.support.utils;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import static com.google.common.base.Preconditions.*;

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
	
	/**
	 * Gera uma senha de size caracteres!
	 * 
	 * @param size
	 * @return
	 */
	public String generate(int size){
		checkArgument(size > 0, "O tamanho da senha deve ser um valor > 0");				
		StringBuilder stringBuilder = new StringBuilder(size);
		for ( int i = 0, indexFonte = new Random().nextInt(FONTES.length + 1); i < size ; i++ ){
			indexFonte = i % FONTES.length;
			stringBuilder.append( RandomStringUtils.random(1, FONTES[indexFonte]) );						
		}
		return stringBuilder.toString();
	}
}
