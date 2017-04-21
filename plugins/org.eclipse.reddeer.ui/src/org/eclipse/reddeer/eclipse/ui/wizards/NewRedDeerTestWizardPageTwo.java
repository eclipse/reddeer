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
package org.eclipse.reddeer.eclipse.ui.wizards;

import org.eclipse.jdt.junit.wizards.NewTestCaseWizardPageTwo;
import org.eclipse.reddeer.ui.Activator;

/**
 * Second page of RedDeer Test Case wizard allows user to select methods that should be
 * tested. Extends JUnit wizard page. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRedDeerTestWizardPageTwo extends NewTestCaseWizardPageTwo {

	protected NewRedDeerTestWizardPageTwo() {
		super();
		setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.REDDEER_ICON));
	}
}