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

package com.github.javarch.support;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

/**
 * Obtém mensagens de i18n nas diversas camadas da arquitetura.
 * 
 * @author Lucas Oliveira
 *
 */
public class MessageSourceAware implements org.springframework.context.MessageSourceAware {
	
	private static MessageSource messageSource;
	
	private MessageSourceAware(){			
	}
	
	/**
	 * Autowired pelo spring
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	/**
	 * Obtém uma mensagem do arquivo de propriedades configurado no applicationContext-commons.xml
	 * 
	 * @param key - Chave da mensagem
	 * @param args - Argumentos da mensagem
	 * @param locale - Locale do usuário
	 * 
	 * @return 
	 */
	public static String getMessage(String key, String [] args , Locale locale){
		try{
			return messageSource.getMessage(key, args, locale);
		}catch (NoSuchMessageException e) {
			return "???" + key + "???";
		}
	}

}
