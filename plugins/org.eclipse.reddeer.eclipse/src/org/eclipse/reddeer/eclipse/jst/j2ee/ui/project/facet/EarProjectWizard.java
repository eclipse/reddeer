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
package org.eclipse.reddeer.eclipse.jst.j2ee.ui.project.facet;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

/**
 * Wizard dialog for creating EAR project.
 */
public class EarProjectWizard extends NewMenuWizard{
	
	public static final String CATEGORY="Java EE";
	public static final String NAME="Enterprise Application Project";
	
	/**
	 * Constructs the wizard with {@value #CATEGORY}.
	 */
	public EarProjectWizard(){
		super("New EAR Application Project",CATEGORY,NAME);
	}

}
