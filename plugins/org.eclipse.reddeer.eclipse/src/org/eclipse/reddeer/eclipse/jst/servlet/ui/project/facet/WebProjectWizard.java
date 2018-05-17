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
package org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

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
