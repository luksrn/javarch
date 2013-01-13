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