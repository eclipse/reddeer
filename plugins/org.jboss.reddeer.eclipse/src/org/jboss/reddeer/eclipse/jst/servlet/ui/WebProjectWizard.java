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
package org.jboss.reddeer.eclipse.jst.servlet.ui;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Wizard dialog for creating web project.
 */
public class WebProjectWizard extends NewWizardDialog{
	
	public static final String CATEGORY="Web";
	public static final String NAME="Dynamic Web Project";
	
	/**
	 * Construct the wizard with {@value #CATEGORY} &gt; {@value #NAME}.
	 */
	public WebProjectWizard(){
		super(CATEGORY,NAME);
	}

}
