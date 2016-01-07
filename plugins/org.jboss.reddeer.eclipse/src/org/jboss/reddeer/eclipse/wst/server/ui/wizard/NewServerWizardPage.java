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
package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.hamcrest.core.StringContains;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.NamedThreadHasStatus;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.condition.WidgetIsEnabled;
import org.jboss.reddeer.swt.impl.button.NextButton;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents the first page displayed when invoked
 * {@link NewServerWizardDialog}
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewServerWizardPage extends WizardPage {

	/**
	 * Select type.
	 *
	 * @param type
	 *            the type
	 */
	public void selectType(String... type) {
		new WaitUntil(new NamedThreadHasStatus(new StringContains("Initializing Servers view"), Thread.State.TERMINATED, true));
		new WaitUntil(new WidgetIsEnabled(new NextButton()));
		new DefaultTreeItem(type).select();
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		new LabeledText("Server name:").setText(name);
	}

	/**
	 * Sets the host name.
	 *
	 * @param name
	 *            the new host name
	 */
	public void setHostName(String name) {
		new LabeledText("Server's host name:").setText(name);
	}
}
