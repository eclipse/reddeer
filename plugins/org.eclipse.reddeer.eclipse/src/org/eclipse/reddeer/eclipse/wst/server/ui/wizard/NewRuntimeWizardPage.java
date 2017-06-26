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

import org.hamcrest.core.StringContains;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.condition.NamedThreadHasStatus;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents the first page displayed when invoked {@link NewRuntimeWizardDialog}
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRuntimeWizardPage extends WizardPage {
  
	/**
	 * Instantiates a new new runtime wizard page.
	 */
	public NewRuntimeWizardPage(ReferencedComposite referencedComposite){
		super(referencedComposite);
	}
	
	/**
	 * Select type.
	 *
	 * @param type the type
	 */
	public void selectType(String... type) {
		new WaitUntil(new NamedThreadHasStatus(new StringContains("Initializing Servers view"), Thread.State.TERMINATED, true));
		new DefaultTreeItem(new DefaultTree(referencedComposite), type).select();
	}
}
