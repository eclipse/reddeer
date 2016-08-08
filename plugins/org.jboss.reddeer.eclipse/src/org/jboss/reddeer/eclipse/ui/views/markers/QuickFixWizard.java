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
package org.jboss.reddeer.eclipse.ui.views.markers;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.impl.button.CancelButton;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * Represents QuickFixWizard 
 * @author rawagner
 *
 */
public class QuickFixWizard {
	
	/**
	 * Default constructor.
	 */
	public QuickFixWizard(){
		new DefaultShell("Quick Fix");
	}
	
	/**
	 * Click cancel button.
	 */
	public void cancel(){
		new CancelButton().click();
		new WaitWhile(new ShellWithTextIsAvailable("Quick Fix"));
	}
	
	/**
	 * Click finish button.
	 */
	public void finish(){
		new FinishButton().click();
	}

}
