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
package org.eclipse.reddeer.eclipse.jst.j2ee.ui.project.facet;

import org.eclipse.reddeer.eclipse.topmenu.NewMenuWizard;

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
