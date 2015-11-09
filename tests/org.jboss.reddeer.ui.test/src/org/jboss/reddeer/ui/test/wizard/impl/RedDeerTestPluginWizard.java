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
 * This class represents a new RedDeer Test Plug-in wizard
 * @author jrichter
 *
 */
public class RedDeerTestPluginWizard extends NewWizardDialog {

	public static final String CATEGORY="RedDeer";
	public static final String NAME="RedDeer Test Plug-in";
	
	/**
	 * Default constructor
	 */
	public RedDeerTestPluginWizard() {
		super(CATEGORY, NAME);
	}
}
