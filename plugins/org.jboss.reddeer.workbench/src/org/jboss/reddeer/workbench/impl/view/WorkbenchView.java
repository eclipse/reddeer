/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.workbench.impl.view;

import org.jboss.reddeer.workbench.api.View;


/**
 * Represents general workbench view.
 * 
 * @author jjankovi
 *
 */
public class WorkbenchView extends AbstractView implements View {
	
	/**
	 * Initialize view with given viewToolTip. 
	 * If view is opened than it will be focused
	 *
	 * @param viewTitle the view title
	 */
	public WorkbenchView(String viewTitle) {
		super(viewTitle);
	}
	
	/**
	 * Initialize view with given viewTitle and category. 
	 * If view is opened than it will be focused
	 * 
	 * @param category of view 
	 * @param viewTitle title of view
	 */
	public WorkbenchView(String category, String viewTitle) {
		super(viewTitle);
		path = new String[2];
		path[0] = category;
		path[1] = viewTitle;
	}
	
}
