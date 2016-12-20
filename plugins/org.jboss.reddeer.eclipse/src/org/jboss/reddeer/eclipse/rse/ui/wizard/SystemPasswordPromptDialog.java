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

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * This class represents Remote System password prompt dialog
 * @author Pavol Srna
 *
 */
public class SystemPasswordPromptDialog extends DefaultShell{

	public static final String TITLE = "Enter Password";
	
	/**
	 * Constructs a dialog with {@value #TITLE}.
	 */
	public SystemPasswordPromptDialog() {
		super(TITLE);
	}
	
	public void setUserID(String username){
		new LabeledText("User ID:").setText(username);
	}
	
	public void setPassword(String password){
		new LabeledText("Password (optional):").setText(password);
	}
	
	public String getUserID(){
		return new LabeledText("User ID:").getText();
	}
	
	public String getPassword(){
		return new LabeledText("Password (optional):").getText();
	}
	
	public void OK(){
		new PushButton("OK").click();
		new WaitWhile(new ShellIsAvailable(this));
	}
	
}
