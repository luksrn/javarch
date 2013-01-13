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

package com.github.javarch.support.config;

import java.util.Properties;

import javax.inject.Inject;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 * 
 * @author Lucas Oliveira
 *
 */
@Configuration
@ComponentScan(basePackages={"com.github.javarch.support"})
@PropertySource("classpath:email.properties")
@Profile("email") // enable email
public class EMailConfig {

	@Inject
	private Environment env;
	
	@Bean
	@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
	public SimpleMailMessage templateMessage(){
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom( env.getRequiredProperty("mail.username") );
		
		return simpleMessage;
	}
	
	/**
	 * http://support.google.com/mail/bin/answer.py?hl=en&answer=13287
	 * @return
	 */
	@Bean
	public MailSender mailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setHost( env.getRequiredProperty("mail.host") );
		mailSender.setPassword( env.getRequiredProperty("mail.password") );
		mailSender.setUsername(env.getRequiredProperty("mail.username"));
		mailSender.setPort( env.getProperty("mail.port", Integer.class, 25) );
		mailSender.setProtocol( env.getRequiredProperty("mail.transport.protocol"));
 
		mailSender.setJavaMailProperties( mailProperties() );
		
		return mailSender;
	}

	protected Properties mailProperties() {
		Properties javaMailProperties = new Properties();
		
		javaMailProperties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth", Boolean.class, false));
		javaMailProperties.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable", Boolean.class, false));

		return javaMailProperties;
	}

}
