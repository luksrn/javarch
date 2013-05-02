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
		 
		if( logger.isDebugEnabled() ){
			PhaseId phaseId = phaseEvent.getPhaseId();
			UIViewRoot uiViewRoot = phaseEvent.getFacesContext().getViewRoot();
	
			String viewId = null;
			if (uiViewRoot != null) {
				viewId = uiViewRoot.getViewId();
			}
	
			logger.debug( phase + " phaseId=[{0}] viewId=[{1}]", phaseId.toString(), viewId);
		}
		
	}
}
