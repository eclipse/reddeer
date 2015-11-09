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

package org.jboss.reddeer.ui.test.wizard.impl;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * This class represents a new RedDeer Test Case wizard
 * @author jrichter
 *
 */
public class RedDeerTestCaseWizard extends NewWizardDialog{

	public static final String NAME = "RedDeer Test"; 
	
	/**
	 * Default constructor
	 */
	public RedDeerTestCaseWizard() {
		super(RedDeerTestPluginWizard.CATEGORY, NAME);
	}
}
