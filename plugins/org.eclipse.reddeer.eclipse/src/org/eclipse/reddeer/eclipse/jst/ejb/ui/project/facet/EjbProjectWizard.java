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
package org.eclipse.reddeer.eclipse.jst.ejb.ui.project.facet;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

/**
 * This class represents EJB Wizard dialog.
 * @author rawagner
 *
 */
public class EjbProjectWizard extends NewMenuWizard {
	
	public static final String CATEGORY="EJB";
	public static final String NAME="EJB Project";
	/**
	 * Default constructor.
	 */
	public EjbProjectWizard() {
		super("New EJB Project",CATEGORY, NAME);
	}

}
