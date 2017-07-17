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
package org.eclipse.reddeer.workbench.core.condition;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;

/**
 * Condition is met if all specified jobs are killed.
 * 
 * @author apodhrad
 *
 */
public class JobIsKilled extends AbstractWaitCondition {

	private static final Logger log = Logger.getLogger(JobIsKilled.class);

	private Job[] currentJobs;
	private Set<String> jobsToBeKilled;
	private Set<String> killedJobs;
	private Job jobToKill;

	/**
	 * Constructs JobIsKilled condition using the specified jobs which should be killed.
	 * 
	 * @param jobsToBeKilled
	 *            Jobs which should be killed
	 */
	public JobIsKilled(String... jobsToBeKilled) {
		this.jobsToBeKilled = new HashSet<>();
		this.killedJobs = new HashSet<>();
		for (String jobToBeKilled : jobsToBeKilled) {
			this.jobsToBeKilled.add(jobToBeKilled);
		}
	}
	
	public JobIsKilled(Job jobToKill){
		this.jobToKill = jobToKill;
		this.killedJobs = new HashSet<>();
	}

	@Override
	public boolean test() {
		if(jobToKill != null){
			log.info("Job '" + jobToKill.getName() + "' will be killed");
			jobToKill.cancel();
			killedJobs.add(jobToKill.getName());
		} else {
			currentJobs = Job.getJobManager().find(null);
			for (Job job : currentJobs) {
				if (jobsToBeKilled.contains(job.getName())) {
					log.info("Job '" + job.getName() + "' will be killed");
					job.cancel();
					killedJobs.add(job.getName());
				}
			}
		}
		currentJobs = Job.getJobManager().find(null);
		for (Job job : currentJobs) {
			if (killedJobs.contains(job.getName())) {
				log.info("The job '" + job.getName() + "' is still alive");
				return false;
			}
		}
		if(jobToKill != null){
			return killedJobs.contains(jobToKill.getName());
		}
		return killedJobs.equals(jobsToBeKilled);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "at least one job is waiting for killing";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#errorMessageWhile()
	 */
	@Override
	public String errorMessageWhile() {
		if(jobToKill != null){
			return "The following job was not killed " + jobToKill.getName();
		}
		return "The following jobs were not killed " + jobsToBeKilled;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#errorMessageUntil()
	 */
	@Override
	public String errorMessageUntil() {
		if(jobToKill != null){
			return "The following job was not killed " + jobToKill.getName();
		}
		return "The following jobs has not been found: " + jobsToBeKilled;
	}

}
