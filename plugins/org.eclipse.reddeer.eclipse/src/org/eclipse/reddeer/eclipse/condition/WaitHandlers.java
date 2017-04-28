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
package org.eclipse.reddeer.eclipse.condition;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.workbench.core.condition.JobIsKilled;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.hamcrest.CoreMatchers;

/**
 * Contains static methods for handling various wait situations 
 * @author rawagner
 *
 */
public class WaitHandlers {
	
	public static final String REFRESHING_SERVER_ADAPTERS = "Refreshing server adapter list";
	
	/**
	 * Handles refreshing servers job
	 */
	public static void handleRefereshingServersJob(){
		JobIsRunning condition = new JobIsRunning(CoreMatchers.is(REFRESHING_SERVER_ADAPTERS));
		new WaitUntil(condition, TimePeriod.DEFAULT, false);
		if(condition.test()){
			for(Job job: Job.getJobManager().find(null)){
				if(job.getName().equals(REFRESHING_SERVER_ADAPTERS)){
					new WaitUntil(new JobIsKilled(job), TimePeriod.LONG);
					break;
				}
			}
		}
	}

}
