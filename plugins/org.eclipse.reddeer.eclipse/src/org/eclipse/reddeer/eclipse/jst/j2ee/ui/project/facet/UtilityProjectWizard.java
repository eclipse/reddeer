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
 * Represents utility project wizard
 * @author rawagner
 *
 */
public class UtilityProjectWizard extends NewMenuWizard{
	
	public static final String CATEGORY="Java EE";
	public static final String NAME="Utility Project";
	
	/**
	 * Constructs the wizard with {@value #NAME}.
	 */
	public UtilityProjectWizard(){
		super("New Java Utility Module",CATEGORY,NAME);
	}

}
