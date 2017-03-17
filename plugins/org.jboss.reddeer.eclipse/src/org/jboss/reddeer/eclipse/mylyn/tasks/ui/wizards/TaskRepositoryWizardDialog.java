/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.mylyn.tasks.ui.wizards;

import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.condition.WidgetIsEnabled;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * Represents Mylyn Task Repository Properties dialog
 * 
 * @author ldimaggi
 *
 */
public class TaskRepositoryWizardDialog extends WizardDialog{
	
	public TaskRepositoryWizardDialog() {
		new DefaultShell("Properties for Task Repository");
	}
	
	/**
	 * Press Validate button - Check for the Validate button before and after it is clicked
	 * as validation can be slow.
	 */
	public void validateSettings(){
		new WaitUntil(new WidgetIsEnabled(new PushButton("Validate Settings")));
		new PushButton("Validate Settings").click();
		new WaitUntil(new WidgetIsEnabled(new PushButton("Validate Settings")));
	}

}
