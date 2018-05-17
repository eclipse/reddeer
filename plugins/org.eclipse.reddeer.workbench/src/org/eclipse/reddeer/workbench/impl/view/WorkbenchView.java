/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.impl.view;

import org.eclipse.reddeer.workbench.api.View;


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
