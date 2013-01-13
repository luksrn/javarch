package com.github.javarch.support;

import java.text.Normalizer;

public class StringUtils {

	/**
	 * Remove qualquer acento de uma String.
	 * 
	 * @param str - 
	 * @return String sem acentos.
	 */
	public static String removeAccents(String str){
		 str = Normalizer.normalize(str, Normalizer.Form.NFD);
		 str = str.replaceAll("[^\\p{ASCII}]", "");
		 return str;
	}
}
