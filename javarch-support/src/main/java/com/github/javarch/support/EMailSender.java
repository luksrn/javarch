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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * TODO Pendente à testes.
 * 
 * @author lucas
 *
 */
@Component
public class EMailSender {
	
	private static final Logger LOG = LoggerFactory.getLogger(EMailSender.class);
	
	@Autowired
	private MailSender sender;

	@Autowired
	private SimpleMailMessage message;
	
	/**
	 * 
	 * @param to
	 * @param assunto
	 * @param msg
	 */
	@Async
	public void send(String [] to, String assunto, String msg){

		message.setSubject(assunto);
		message.setText(msg);
		message.setTo(to);

		LOG.debug("Iniciando o envio da mensagem async: {}", message);
		
		sender.send(message);
		
		LOG.debug("Mensagem Enviada.");
	}
}
