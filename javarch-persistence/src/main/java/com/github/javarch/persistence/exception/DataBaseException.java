package com.github.javarch.persistence.exception;

import com.github.javarch.persistence.aop.JavarchPersistenceExceptionTranslate;

/**
 * Exception lancada pelo {@link JavarchPersistenceExceptionTranslate}.
 * 
 * s
 * @author Lucas Oliveira 
 *
 */
public class DataBaseException extends RuntimeException {
 
	private static final long serialVersionUID = 1839603709736689594L;

	public DataBaseException(String mensagem){
		super(mensagem);
	}
	
	public DataBaseException(String mensagem, Throwable rootCouse ){
		super(mensagem,rootCouse);
	}
}
