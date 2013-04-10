package com.github.javarch.persistence.orm.hibernate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ParameterMapper;

import com.google.common.base.Preconditions;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;


public class StoredProcedure extends org.springframework.jdbc.object.StoredProcedure {
	

	@Override
	public Map<String, Object> execute(Map<String, ?> inParams) throws DataAccessException {
		
		checkStateConsultaSql();		 

		final TimerContext context = getTimerContext();
		try { 
			return super.execute(inParams);
		} finally {
			context.stop();
		}
		
	}

	
	@Override
	public Map<String, Object> execute(ParameterMapper inParamMapper)	throws DataAccessException {
		
		checkStateConsultaSql(); 
		final TimerContext context = getTimerContext();
		try { 
			return super.execute(inParamMapper);
		} finally {
			context.stop();
		}
	}


	@Override
	public Map<String, Object> execute(Object... inParams) {
		
		checkStateConsultaSql();	 
		final TimerContext context = getTimerContext();
		try { 
			return super.execute(inParams);
		} finally {
			context.stop();
		}
	}
	
	private void checkStateConsultaSql() {
		Preconditions.checkState( getSql() != null , "Nenhuma SQL foi definida na StoredProcedure. Veja setSql() em " + getClass().getSimpleName() );
	}
	
	private TimerContext getTimerContext() {
		Timer timer = Metrics.newTimer(StoredProcedure.class, "execute-procedure-" + getSql() , TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
		return timer.time();
	}

}
