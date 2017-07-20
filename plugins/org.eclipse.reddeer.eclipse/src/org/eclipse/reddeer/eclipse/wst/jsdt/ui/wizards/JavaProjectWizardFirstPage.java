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
package org.eclipse.reddeer.eclipse.wst.jsdt.ui.wizards;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Represents the first page displayed when invoked {@link JavaProjectWizard}
 * 
 * @author Pavol Srna
 *
 */
public class JavaProjectWizardFirstPage  extends WizardPage {
	
	public JavaProjectWizardFirstPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets project name.
	 *
	 * @param name the new name
	 */
	public JavaProjectWizardFirstPage setName(String name){
		new LabeledText(referencedComposite, "Project name:").setText(name);
		return this;
	}
}
