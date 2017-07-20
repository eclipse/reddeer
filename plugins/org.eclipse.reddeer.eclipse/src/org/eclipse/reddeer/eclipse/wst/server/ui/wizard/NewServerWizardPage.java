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
package org.eclipse.reddeer.eclipse.wst.server.ui.wizard;

import static org.eclipse.reddeer.common.wait.WaitProvider.waitUntil;

import org.hamcrest.core.StringContains;
import org.eclipse.reddeer.common.wait.GroupWait;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.condition.NamedThreadHasStatus;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.condition.TreeContainsItem;
import org.eclipse.reddeer.swt.condition.ControlIsEnabled;
import org.eclipse.reddeer.swt.impl.button.NextButton;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents the first page displayed when invoked
 * {@link NewServerWizard}
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewServerWizardPage extends WizardPage {
	
	public NewServerWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Select type.
	 *
	 * @param type
	 *            the type
	 */
	public NewServerWizardPage selectType(String... type) {
		new GroupWait(waitUntil(new NamedThreadHasStatus(new StringContains("Initializing Servers view"), Thread.State.TERMINATED, true)),
				waitUntil(new TreeContainsItem(new DefaultTree(referencedComposite), type)));
		new DefaultTreeItem(new DefaultTree(referencedComposite), type).select();
		new WaitUntil(new ControlIsEnabled(new NextButton(referencedComposite)));
		return this;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public NewServerWizardPage setName(String name) {
		new LabeledText(referencedComposite, "Server name:").setText(name);
		return this;
	}

	/**
	 * Sets the host name.
	 *
	 * @param name
	 *            the new host name
	 */
	public NewServerWizardPage setHostName(String name) {
		new LabeledText(referencedComposite, "Server's host name:").setText(name);
		return this;
	}
}
