package com.github.javarch.jsf.context;


import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.github.javarch.support.log.Logger;
import com.github.javarch.support.log.LoggerFactory;
 
 
 
/** 
 */
public class DebugPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 5431973221176870776L;

	private static final Logger logger = LoggerFactory.getLogger(DebugPhaseListener.class);

	public void beforePhase(PhaseEvent phaseEvent) {
		debugPhase("BEFORE",phaseEvent);
	}	

	public void afterPhase(PhaseEvent phaseEvent) {
		debugPhase("AFTER",phaseEvent);
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
	
	private void debugPhase(String phase, PhaseEvent phaseEvent) {
		 
		PhaseId phaseId = phaseEvent.getPhaseId();
		UIViewRoot uiViewRoot = phaseEvent.getFacesContext().getViewRoot();

		String viewId = null;
		if (uiViewRoot != null) {
			viewId = uiViewRoot.getViewId();
		}

		logger.debug( phase + " phaseId=[{0}] viewId=[{1}]", phaseId.toString(), viewId);
		
		
	}
}
