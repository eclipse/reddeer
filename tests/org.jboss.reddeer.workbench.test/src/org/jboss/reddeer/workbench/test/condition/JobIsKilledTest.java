package org.jboss.reddeer.workbench.test.condition;

import static org.jboss.reddeer.common.wait.WaitProvider.*;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.wait.GroupWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.core.condition.JobIsKilled;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;
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
	public static final String TEST_JOB_EXCEPTION_NAME = "Test job Exception";

	@Test
	public void testKillJob() {
		Job testJob = new Job(TEST_JOB_NAME) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				long sleepTime = 60000;
				long expectedtime = System.currentTimeMillis() + sleepTime;
				while (System.currentTimeMillis() < expectedtime) {
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
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
		Job testJob = new Job(TEST_JOB_EXCEPTION_NAME) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				long sleepTime = 60000;
				long expectedtime = System.currentTimeMillis() + sleepTime;
				while (System.currentTimeMillis() < expectedtime) {
					// wait
				}
				return Status.OK_STATUS;
			}
		};
		testJob.schedule();

		try {
			new GroupWait(waitUntil(new JobHasState(testJob, Job.RUNNING)),
					waitUntil(new JobIsKilled(TEST_JOB_EXCEPTION_NAME)));
		} catch (WaitTimeoutExpiredException wte) {
			new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
			return;
		}
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		Assert.fail("No exception was thrown");
	}
	
	private class JobHasState extends AbstractWaitCondition {
		
		private Job job;
		private int state;
		
		public JobHasState(Job job, int state) {
			this.job = job;
			this.state = state;
		}

		@Override
		public boolean test() {
			return job.getState() == state;
		}
	}
}
