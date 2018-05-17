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
package org.eclipse.reddeer.eclipse.rse.ui.wizards.newconnection;

import org.eclipse.reddeer.eclipse.selectionwizard.NewMenuWizard;

/**
 * This class represents the New Connection wizard dialog.
 * @author Pavol Srna
 *
 */
public class RSEMainNewConnectionWizard extends NewMenuWizard{	
	public static final String TITLE = "New Connection";
	
	/**
	 * Constructs the wizard with "Remote System Explorer" &gt; {@value #TITLE}.
	 */
	public RSEMainNewConnectionWizard() {
		super(TITLE, "Remote System Explorer", "Connection");
	}

}
