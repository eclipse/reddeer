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
package org.eclipse.reddeer.eclipse.ui.wizards.newresource;

import org.eclipse.reddeer.eclipse.topmenu.NewMenuWizard;
/**
 * Represents new file creation wizard dialog (General &gt; File)
 * 
 * @author jjankovi
 *
 */
public class BasicNewFileResourceWizard extends NewMenuWizard {
	/**
	 * Constructs the wizard with "General" &gt; "File".
	 */
	public BasicNewFileResourceWizard() {
		super("New File","General", "File");
	}
}
