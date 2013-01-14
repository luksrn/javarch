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
