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
package org.eclipse.reddeer.eclipse.m2e.scm.wizards;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

/**
 * New Maven SCM Project Wizard Dialog
 * @author rawagner
 *
 */
public class MavenCheckoutWizard extends NewMenuWizard{
	
	public static final String CATEGORY="Maven";
	public static final String NAME="Check out Maven Projects from SCM";
	

	/**
	 * Default constructor.
	 */
	public MavenCheckoutWizard(){
		super("Check out as Maven project from SCM",CATEGORY,NAME);
	}

}
