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
