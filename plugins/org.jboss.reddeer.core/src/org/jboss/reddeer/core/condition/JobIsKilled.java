package org.jboss.reddeer.core.condition;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.jobs.Job;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.logging.Logger;

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

	@Override
	public boolean test() {
		currentJobs = Job.getJobManager().find(null);
		for (Job job : currentJobs) {
			if (jobsToBeKilled.contains(job.getName())) {
				log.info("Job '" + job.getName() + "' will be killed");
				job.cancel();
				killedJobs.add(job.getName());
			}
		}
		currentJobs = Job.getJobManager().find(null);
		for (Job job : currentJobs) {
			if (killedJobs.contains(job.getName())) {
				log.info("The job '" + job.getName() + "' is still alive");
				return false;
			}
		}
		return killedJobs.equals(jobsToBeKilled);
	}

	@Override
	public String description() {
		return "at least one job is waiting for killing";
	}

	@Override
	public String errorMessage() {
		return "The following jobs were not killed " + jobsToBeKilled;
	}

}
