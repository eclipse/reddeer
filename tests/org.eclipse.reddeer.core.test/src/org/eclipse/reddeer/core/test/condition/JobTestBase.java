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
package org.eclipse.reddeer.core.test.condition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.junit.After;

/**
 * Test base with basic job support. Runs jobs and system jobs, asserts that job is running 
 * and kills all scheduled jobs after test.
 *
 * @author Jan Novak <jnovak@redhat.com>
 */
public abstract class JobTestBase {
	
	/**
	 * Stores scheduled jobs for 'kill' after method and asserts.
	 */
	private final Map<String, Job> scheduledJobs = new HashMap<>();
	private final Logger log = Logger.getLogger(JobTestBase.class);
	
	/**
	 * Asserts that job with specified name is running.
	 * @param name job name
	 */
	protected void assertRunning(String name) {
		assertTrue("Job '" + name + "' is not running!", isRunning(name));
	}

	/**
	 * Asserts that job with specified name is not running.
	 * @param name job name
	 */
	protected void assertNotRunning(String name) {
		assertFalse("Job '" + name + "' is not finisched yet!", isRunning(name));
	}
	
	@After
	public void killScheduledJobs() {
		for (Job job : scheduledJobs.values()) {
			if (isRunning(job)) {
				try {
					killJob(job);
				} catch (CannotKillJobException e) {
					log.error(e.getMessage(), e);
					fail(e.getMessage());
				}
			}
		}
		scheduledJobs.clear();
	}

	/**
	 * Runs new parallel job.
	 * @param name job name
	 * @param seconds running time in seconds
	 */
	protected void runParallelyForSeconds(String name, int seconds) {
		runJob(name, seconds, false);
	}

	/**
	 * Runs new parallel system job.
	 * @param name job name
	 * @param seconds running time in seconds
	 */
	protected void runParallelyForSecondsAsSystem(String name, int seconds) {
		runJob(name, seconds, true);
	}

	private void runJob(final String name, final int seconds, final boolean isSystem) {
		Job job = new Job(name) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					for (int i = 0; i < seconds; i++) {
						if (monitor.isCanceled()) {
							return Status.CANCEL_STATUS;
						}
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(isSystem);
		ensureRunning(job);
	}

	private void ensureRunning(final Job job) {
		scheduledJobs.put(job.getName(), job);
		job.schedule();

		new WaitUntil(new AbstractWaitCondition() {
			@Override
			public boolean test() {
				return isRunning(job);
			}
		}, TimePeriod.DEFAULT);
	}
	
	private boolean isRunning(final String name) {
		return isRunning(scheduledJobs.get(name));
	}

	private boolean isRunning(final Job job) {
		return job != null && Job.RUNNING == job.getState();
	}
	
	private void killJob(Job job) throws CannotKillJobException {
		for (int i = 1, maxAttempts = 3; i <= maxAttempts; i++) {
			boolean success = job.cancel();
			if (success) {
				log.info("Job '" + job.getName() + "' was successfully killed");
				return;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new CannotKillJobException(e);
			}
		}
		throw new CannotKillJobException("Job '" + job.getName() + "' was not killed!");
	}

	private class CannotKillJobException extends Exception {

		private static final long serialVersionUID = 4759327093361777991L;

		CannotKillJobException(String msg) {
			super(msg);
		}

		CannotKillJobException(Throwable throwable) {
			super(throwable);
		}
	}
}
