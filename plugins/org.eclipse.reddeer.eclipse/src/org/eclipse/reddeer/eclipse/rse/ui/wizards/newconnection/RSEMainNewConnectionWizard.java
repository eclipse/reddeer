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
package org.eclipse.reddeer.eclipse.rse.ui.wizards.newconnection;

import org.eclipse.reddeer.eclipse.topmenu.NewMenuWizard;

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
