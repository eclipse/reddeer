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

import org.eclipse.core.runtime.jobs.Job;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;

/**
 * Condition is met when there is/are running non-system job(s).
 * List of jobs can be filtered using matchers.
 * 
 * @author Lucia Jelinkova
 */
@SuppressWarnings("rawtypes")
public class JobIsRunning extends AbstractWaitCondition {
	private static final Logger log = Logger.getLogger(JobIsRunning.class);

	private Matcher[] consideredJobs;
	private Matcher[] excludeJobs;
	private boolean skipSystemJobs;
	private Job[] currentJobs;

	/**
	 * Constructs JobIsRunning wait condition. Condition is met when job is running.
	 */
	public JobIsRunning() {
		this(null, null);
	}

	/**
	 * Constructs JobIsRunning wait condition. Condition is met when job is running.
	 * Test only jobs matching the specified matcher. Skips all system jobs.
	 * 
	 * @param consideredJob checks only jobs matching a specified matcher. Use in case
	 * you want to make sure all jobs matching a specified matcher are running and 
	 * you do not care about other jobs.
	 */
	public JobIsRunning(Matcher consideredJob) {
		this(new Matcher[] {consideredJob});
	}
	
	/**
	 * Constructs JobIsRunning wait condition. Condition is met when job is running.
	 * Test only jobs matching the specified matcher. Can enable/disable check on
	 * system jobs.
	 * 
	 * @param consideredJob checks only jobs matching a specified matcher. Use in case
	 * you want to make sure all jobs matching a specified matcher are running and 
	 * you do not care about other jobs.
	 * @param skipSystemJobs if true, then all system jobs are skipped
	 */
	public JobIsRunning(Matcher consideredJob, boolean skipSystemJobs) {
		this(new Matcher[] {consideredJob}, null, skipSystemJobs);
	}
	
	/**
	 * Constructs JobIsRunning wait condition. Condition is met when job(s) is/are running.
	 * Test only jobs matching the specified matchers. Skips all system jobs.
	 * 
	 * @param consideredJobs If not <code>null</code>, only jobs whose name matches
	 * any of these matchers will be tested. Use in case you want to make sure all
	 * jobs from a limited set are not running, and you don't care about the rest
	 * of jobs.
	 */
	public JobIsRunning(Matcher[] consideredJobs) {
		this(consideredJobs, null);
	}

	/**
	 * Constructs JobIsRunning wait condition. Condition is met when job(s) is/are running.
	 * Test only jobs matching the specified matchers which are not excluded by 
	 * another specified matchers. Skips all system jobs.
	 * 
	 * @param consideredJobs If not <code>null</code>, only jobs whose name matches
	 * any of these matchers will be tested. Use in case you want to make sure all
	 * jobs from a limited set are not running, and you don't care about the rest
	 * of jobs.
	 * @param excludeJobs If not <code>null</code>, jobs whose name matches any of
	 * these matcher will be ignored. Use in case you don't care about limited set
	 * of jobs. These matchers will overrule <code>consideredJobs</code> results,
	 * job matched by both <code>consideredJobs</code> and <code>excludeJobs</code>
	 * will be excluded.
	 */
	public JobIsRunning(Matcher[] consideredJobs, Matcher[] excludeJobs) {
		this(consideredJobs, excludeJobs, true);
	}

	/**
	 * Constructs JobIsRunning wait condition. Condition is met when job(s) is/are running.
	 * Test only jobs matching the specified matchers which are not excluded by 
	 * another specified matchers.
	 * 
	 * @param consideredJobs If not <code>null</code>, only jobs whose name matches
	 * any of these matchers will be tested. Use in case you want to make sure all
	 * jobs from a limited set are not running, and you don't care about the rest
	 * of jobs.
	 * @param excludeJobs If not <code>null</code>, jobs whose name matches any of
	 * these matcher will be ignored. Use in case you don't care about limited set
	 * of jobs. These matchers will overrule <code>consideredJobs</code> results,
	 * job matched by both <code>consideredJobs</code> and <code>excludeJobs</code>
	 * will be excluded.
	 * @param skipSystemJobs If true then all system jobs are skipped.
	 */
	public JobIsRunning(Matcher[] consideredJobs, Matcher[] excludeJobs, boolean skipSystemJobs) {
		this.consideredJobs = consideredJobs;
		this.excludeJobs = excludeJobs;
		this.skipSystemJobs = skipSystemJobs;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean test() {
		currentJobs = Job.getJobManager().find(null);
		for (Job job: currentJobs) {
			if (excludeJobs != null && CoreMatchers.anyOf(excludeJobs).matches(job.getName())) {
				log.debug("  job '%s' specified by excludeJobs matchers, skipped", job.getName());
				continue;
			}

			if (consideredJobs != null && !CoreMatchers.anyOf(consideredJobs).matches(job.getName())) {
				log.debug("  job '%s' is not listed in considered jobs, ignore it", job.getName());
				continue;
			}

			if (skipSystemJobs && job.isSystem()) { 
				log.debug("  job '%s' is a system job, skipped", job.getName());
				continue;
			}
			
			if (job.getState() == Job.SLEEPING) {
				log.debug("  job '%s' is not running, skipped", job.getName());
				continue;
			}

			/* there's no reason why this one should be ignored, lets wait... */
			log.debug("  job '%s' has no excuses, wait for it", job.getName());
			return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "at least one job is running";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#errorMessageWhile()
	 */
	@Override
	public String errorMessageWhile() {
		return createErrorMesssageWithJobsList("The following jobs are still running:\n");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#errorMessageUntil()
	 */
	@Override
	public String errorMessageUntil() {
		return createErrorMesssageWithJobsList("The following jobs are not running:\n");
	}
	
	/**
	 * Creates error message for methods errorMessageWhile() and errorMessageUntil() with job names.
	 * 
	 * @param messageStart start of the error message with job list
	 */
	@SuppressWarnings("unchecked")
	private String createErrorMesssageWithJobsList(String messageStart){
		StringBuilder msg = new StringBuilder(messageStart);
		for (Job job: currentJobs) {
			if (excludeJobs != null && CoreMatchers.anyOf(excludeJobs).matches(job.getName()))
				continue;
			if (consideredJobs != null && !CoreMatchers.anyOf(consideredJobs).matches(job.getName()))
				continue;
			if (skipSystemJobs && job.isSystem()) 
				continue;
			if (job.getState() == Job.SLEEPING)
				continue;
			msg.append("\t").append(job.getName()).append("\n");
		}
		return msg.toString();
	}
}
