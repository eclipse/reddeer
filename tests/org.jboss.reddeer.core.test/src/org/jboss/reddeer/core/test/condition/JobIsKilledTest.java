package org.jboss.reddeer.core.test.condition;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsKilled;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author apodhrad
 *
 */
@RunWith(RedDeerSuite.class)
public class JobIsKilledTest {

	public static final String TEST_JOB_NAME = "Test job";

	@Test
	public void testKillJob() {
		Job testJob = new Job(TEST_JOB_NAME) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					for (int i = 0; i < 60; i++) {
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
		testJob.schedule();

		new WaitUntil(new JobIsKilled(TEST_JOB_NAME));

		Job[] currentJobs = Job.getJobManager().find(null);
		for (Job job : currentJobs) {
			if (TEST_JOB_NAME.equals(job.getName())) {
				Assert.fail("Job '" + TEST_JOB_NAME + "' wasn't properly killed!");
			}
		}
	}
	
	@Test
	public void testKillJobException() {
		Job testJob = new Job(TEST_JOB_NAME) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					for (int i = 0; i < 60; i++) {
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return Status.OK_STATUS;
			}
		};
		testJob.schedule();

		try {
			new WaitUntil(new JobIsKilled(TEST_JOB_NAME));
		} catch (WaitTimeoutExpiredException wte) {
			new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
			return;
		}
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		Assert.fail("No exception was thrown");
	}
}
