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

import org.jboss.reddeer.jface.wizard.WizardDialog;

/**
 * Represents the wizard for creating new servers. It provides access to the first wizard page {@link NewRuntimeWizardPage}. 
 * Since the other pages depend on the selection of the concrete runtime type this wizard does not provide them.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRuntimeWizardDialog extends WizardDialog{

	public static final String DIALOG_TITLE = "New Server Runtime Environment";
}
