package com.github.javarch.jsf.context;


import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
 
 


/**
 * @author  Neil Griffin
 */
public class DebugPhaseListener implements PhaseListener {

	// serialVersionUID
	private static final long serialVersionUID = 5431973221176870776L;

	// Logger
	private static final Logger logger = null;//LoggerFactory.getLogger(DebugPhaseListener.class);

	public void afterPhase(PhaseEvent phaseEvent) {

		if (logger.isDebugEnabled()) {
			PhaseId phaseId = phaseEvent.getPhaseId();

			String viewId = null;
			UIViewRoot uiViewRoot = phaseEvent.getFacesContext().getViewRoot();

			if (uiViewRoot != null) {
				viewId = uiViewRoot.getViewId();
			}

			logger.debug("AFTER phaseId=[{0}] viewId=[{1}]", phaseId.toString(), viewId);
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {

		if (logger.isDebugEnabled()) {
			PhaseId phaseId = phaseEvent.getPhaseId();

			String viewId = null;
			UIViewRoot uiViewRoot = phaseEvent.getFacesContext().getViewRoot();

			if (uiViewRoot != null) {
				viewId = uiViewRoot.getViewId();
			}

			logger.debug("BEFORE phaseId=[{0}] viewId=[{1}]", phaseId.toString(), viewId);
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
