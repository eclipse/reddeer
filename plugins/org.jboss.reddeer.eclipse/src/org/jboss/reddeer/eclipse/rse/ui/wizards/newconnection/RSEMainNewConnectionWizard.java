/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.rse.ui.wizards.newconnection;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * This class represents the New Connection wizard dialog.
 * @author Pavol Srna
 *
 */
public class RSEMainNewConnectionWizard extends NewWizardDialog{
	
	public static final String TITLE = "New Connection";
	
	/**
	 * Constructs the wizard with "Remote System Explorer" &gt; {@value #TITLE}.
	 */
	public RSEMainNewConnectionWizard() {
		super("Remote System Explorer", "Connection");
	}

}
