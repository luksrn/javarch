package com.github.javarch.persistence.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.javarch.persistence.exception.DataBaseException;

@Aspect
@Component
public class JavarchPersistenceExceptionTranslate {

	private static final Logger LOG = LoggerFactory.getLogger(JavarchPersistenceExceptionTranslate.class);
	
	@AfterThrowing(
			pointcut="execution(* com.github.javarch.persistence.Repository.*(..))",
			throwing="ex")
	public void afterThrowing(Exception ex) throws Throwable {
	   throwsDataBaseException(ex);
    }

	@AfterThrowing(
			pointcut="execution(* com.github.javarch.persistence.orm.hibernate.HibernateRepository.*(..))",
			throwing="ex")
	public void auditErrorHibernateRepository(JoinPoint joinPoint, Exception ex){
		 throwsDataBaseException(ex);
	}
	
	private void throwsDataBaseException(Exception ex) {
		LOG.debug("Tranduzindo exceção {} em DabaBaseException", ex.getClass().getName() );
		DataBaseException dbException = new DataBaseException( ex.getMessage() , ex);
		throw dbException;
	}
}
