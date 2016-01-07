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
package org.jboss.reddeer.eclipse.ui.wizards;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.junit.wizards.NewTestCaseWizardPageTwo;
import org.eclipse.jface.resource.ImageDescriptor;
import org.jboss.reddeer.ui.Activator;

/**
 * Second page of Red Deer Test Case wizard allows user to select methods that should be
 * tested. Extends JUnit wizard page. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRedDeerTestWizardPageTwo extends NewTestCaseWizardPageTwo {

	protected NewRedDeerTestWizardPageTwo() {
		super();
		setImageDescriptor(ImageDescriptor.createFromURL(FileLocator.find(
				Platform.getBundle(Activator.PLUGIN_ID), new Path(
						"resources/reddeer_icon.png"), null)));
	}
}