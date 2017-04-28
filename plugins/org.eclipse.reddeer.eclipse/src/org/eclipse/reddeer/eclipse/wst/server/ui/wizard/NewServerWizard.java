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
import org.eclipse.reddeer.eclipse.condition.WaitHandlers;
import org.eclipse.reddeer.eclipse.topmenu.NewMenuWizard;
import org.eclipse.reddeer.workbench.core.condition.JobIsKilled;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.core.runtime.jobs.Job;
import org.hamcrest.CoreMatchers;
/**
 * Represents the wizard for creating new servers. It provides access to the first wizard page {@link NewServerWizardPage}. 
 * Since the other pages depend on the selection of the concrete server type this wizard does not provide them.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewServerWizard extends NewMenuWizard {
	public static final String TITLE = "New Server";
	
	/**
	 * Instantiates a new new server wizard dialog.
	 */
	public NewServerWizard() {
		super(TITLE, "Server", "Server");
	}

	@Override
	public void finish(TimePeriod timeout) {
		// workaround due to JBDS-3596
		WaitHandlers.handleRefereshingServersJob();
		super.finish(timeout);
	}

	@Override
	public void cancel() {
		// workaround due to JBDS-3596
		WaitHandlers.handleRefereshingServersJob();
		super.cancel();
	}
	
}
