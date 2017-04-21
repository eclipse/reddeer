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
package org.eclipse.reddeer.eclipse.mylyn.tasks.ui.wizards;

import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.condition.ControlIsEnabled;
import org.eclipse.reddeer.swt.impl.button.PushButton;

/**
 * Represents Mylyn Task Repository Properties dialog
 * 
 * @author ldimaggi
 *
 */
public class TaskRepositoryWizardDialog extends WizardDialog{
	
	public TaskRepositoryWizardDialog() {
		super("Properties for Task Repository");
	}
	
	/**
	 * Press Validate button - Check for the Validate button before and after it is clicked
	 * as validation can be slow.
	 */
	public void validateSettings(){
		new WaitUntil(new ControlIsEnabled(new PushButton("Validate Settings")));
		new PushButton("Validate Settings").click();
		new WaitUntil(new ControlIsEnabled(new PushButton("Validate Settings")));
	}

}
