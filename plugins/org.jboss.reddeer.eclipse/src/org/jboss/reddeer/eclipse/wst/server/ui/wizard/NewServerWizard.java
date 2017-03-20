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

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.eclipse.topmenu.NewMenuWizard;
import org.jboss.reddeer.workbench.core.condition.JobIsKilled;

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
