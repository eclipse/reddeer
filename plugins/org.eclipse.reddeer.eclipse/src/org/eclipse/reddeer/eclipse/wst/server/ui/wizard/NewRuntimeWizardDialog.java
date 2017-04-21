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

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.workbench.core.condition.JobIsKilled;

/**
 * Represents the wizard for creating new servers. It provides access to the first wizard page {@link NewRuntimeWizardPage}. 
 * Since the other pages depend on the selection of the concrete runtime type this wizard does not provide them.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRuntimeWizardDialog extends WizardDialog{
	
	public static final String DIALOG_TITLE = "New Server Runtime Environment";
	
	public NewRuntimeWizardDialog() {
		super(DIALOG_TITLE);
	}

	@Override
	public void finish(TimePeriod timeout) {
		// workaround due to JBDS-3596
		new WaitUntil(new JobIsKilled("Refreshing server adapter list"), TimePeriod.LONG, false);
		super.finish(timeout);
	}

	@Override
	public void cancel() {
		// workaround due to JBDS-3596
		new WaitUntil(new JobIsKilled("Refreshing server adapter list"), TimePeriod.LONG, false);
		super.cancel();
	}
	
}
