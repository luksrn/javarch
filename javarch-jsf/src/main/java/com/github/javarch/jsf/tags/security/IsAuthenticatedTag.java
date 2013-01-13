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

package com.github.javarch.jsf.tags.security;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagHandler;

/**
 * Taglib to combine the Spring-Security Project with Facelets <br />
 *
 * This is the Class responsible for making the <br />
 * <code><br />
 *     &lt;sec:isAuthenticated;&gt;<br />
 *         The components you want to show only when the user is authenticated<br />
 *     lt;/sec:isAuthenticated&gt;<br />
 * </code>
 * work.
 *
 *
 * @author Grzegorz Blaszczyk http://dominikdorn.com/
 */
public class IsAuthenticatedTag extends TagHandler {


	public IsAuthenticatedTag(ComponentConfig componentConfig) {
		super(componentConfig);	
	}
	
	public void apply(FaceletContext faceletContext, UIComponent uiComponent)
			throws IOException {

		if(SpringSecurityELLibrary.isAuthenticated()) {
			this.nextHandler.apply(faceletContext, uiComponent);
		}
	}

}