/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet;

import org.eclipse.reddeer.eclipse.topmenu.NewMenuWizard;

/**
 * Wizard dialog for creating web project.
 */
public class WebProjectWizard extends NewMenuWizard{
	
	public static final String CATEGORY="Web";
	public static final String NAME="Dynamic Web Project";
	
	/**
	 * Construct the wizard with {@value #CATEGORY} &gt; {@value #NAME}.
	 */
	public WebProjectWizard(){
		super("New Dynamic Web Project",CATEGORY,NAME);
	}

}
