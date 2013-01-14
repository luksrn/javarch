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
package com.github.javarch.persistence.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditorThrowingException {

	private static final Logger LOG = LoggerFactory.getLogger(AuditorThrowingException.class);
	
	@AfterThrowing(
			pointcut="execution(* com.github.javarch.persistence.Repository.*(..))",
			throwing="ex")
	public void auditErrorRepository(JoinPoint joinPoint, Exception ex){
		LOG.debug("Um erro aconteceu na camada do método {} acesso aos dados: {}", joinPoint.getSignature().getName(), ex.getMessage() );
	}
	
	@AfterThrowing(
			pointcut="execution(* com.github.javarch.persistence.orm.hibernate.HibernateRepository.*(..))",
			throwing="ex")
	public void auditErrorHibernateRepository(JoinPoint joinPoint, Exception ex){
		LOG.debug("Um erro aconteceu na camada do método {} acesso aos dados: {}", joinPoint.getSignature().getName(), ex.getMessage() );
	}
	
}