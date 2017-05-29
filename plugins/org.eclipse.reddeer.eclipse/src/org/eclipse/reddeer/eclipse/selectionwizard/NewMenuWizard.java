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
package org.eclipse.reddeer.eclipse.selectionwizard;

import org.eclipse.reddeer.eclipse.ui.dialogs.NewWizard;
import org.eclipse.reddeer.jface.window.Openable;

/**
 * Represents wizard which can be found in new wizard dialog (menu 'File -&gt; New -&gt; Other...').
 * @author rawagner
 *
 */
public abstract class NewMenuWizard extends AbstractSelectionWizardDialog{
	
	/**
	 * Constructs new Wizard that can be found in NewWizard.
	 * @param shellText new wizard text when next is clicked (ie 'New File')
	 * @param wizardCategory new wizard category (ie 'General')
	 * @param wizardName new wizard name (ie 'File')
	 */
	public NewMenuWizard(String shellText, String wizardCategory, String wizardName) {
		super(shellText, wizardCategory, wizardName);
	}
	
	/**
	 * Constructs new Wizard that can be found in NewWizard.
	 * @param shellText new wizard text when next is clicked (ie 'New File')
	 * @param wizardPath wizard path in new wizard (ie 'General, File')
	 */
	public NewMenuWizard(String shellText, String[] wizardPath) {
		super(shellText,wizardPath);
	}
	
	@Override
	protected Openable getOpenAction() {
		return new SelectionWizardOpenable(new NewWizard(), wizardPath, matcher);
	}
}