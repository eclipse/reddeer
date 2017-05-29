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
package org.eclipse.reddeer.eclipse.m2e.core.ui.wizard;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

/**
 * New Maven Project Wizard Dialog
 * @author rawagner
 *
 */
public class MavenProjectWizard extends NewMenuWizard{
	
	public static final String CATEGORY="Maven";
	public static final String NAME="Maven Project";
	
	/**
	 * Default constructor.
	 */
	public MavenProjectWizard(){
		super("New Maven Project",CATEGORY,NAME);
	}

}
