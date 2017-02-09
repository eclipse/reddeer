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
package org.jboss.reddeer.eclipse.ui.wizards.datatransfer;

import org.jboss.reddeer.jface.wizard.ImportWizardDialog;

/**
 * Wizard for importing external projects into the workspace. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ExternalProjectImportWizardDialog extends ImportWizardDialog {

	/**
	 * Construct the wizard with "General" &gt; "Existing Projects into Workspace".
	 */
	public ExternalProjectImportWizardDialog() {
		super(new String[]{"General", "Existing Projects into Workspace"});
	}
}
