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

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.JobIsKilled;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.jface.wizard.NewWizardDialog;

/**
 * Represents the wizard for creating new servers. It provides access to the first wizard page {@link NewServerWizardPage}. 
 * Since the other pages depend on the selection of the concrete server type this wizard does not provide them.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewServerWizardDialog extends NewWizardDialog {

	public static final String TITLE = "New Server";
	
	private static final String REFRESHING_JOB_NAME = "Refreshing server adapter list";
	
	/**
	 * Instantiates a new new server wizard dialog.
	 */
	public NewServerWizardDialog() {
		super("Server", "Server");
	}

	@Override
	public void finish(TimePeriod timeout) {
		// workaround due to JBDS-3596
		Matcher[] jobs = new Matcher[1];
		jobs[0] = new IsEqual<String>(REFRESHING_JOB_NAME);
		new WaitUntil(new JobIsRunning(jobs),TimePeriod.NORMAL,false);
		new WaitUntil(new JobIsKilled(REFRESHING_JOB_NAME), TimePeriod.LONG, false);
		super.finish(timeout);
	}

	@Override
	public void cancel() {
		// workaround due to JBDS-3596
		Matcher[] jobs = new Matcher[1];
		jobs[0] = new IsEqual<String>(REFRESHING_JOB_NAME);
		new WaitUntil(new JobIsRunning(jobs),TimePeriod.NORMAL,false);
		new WaitUntil(new JobIsKilled(REFRESHING_JOB_NAME), TimePeriod.LONG, false);
		super.cancel();
	}
	
}
