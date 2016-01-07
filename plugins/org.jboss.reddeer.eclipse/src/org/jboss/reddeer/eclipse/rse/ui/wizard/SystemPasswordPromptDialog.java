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
package org.jboss.reddeer.eclipse.rse.ui.wizard;

import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * This class represents Remote System password prompt dialog
 * @author Pavol Srna
 *
 */
public class SystemPasswordPromptDialog {

	public static final String TITLE = "Enter Password";
	
	private Shell shell;
	
	/**
	 * Constructs a dialog with {@value #TITLE}.
	 */
	public SystemPasswordPromptDialog() {
		shell = new DefaultShell(TITLE);
	}
	
	/**
	 * Get Shell of the Remote System password prompt dialog.
	 *
	 * @return Shell
	 */
	public Shell getShell(){
		return this.shell;
	}
	
}
