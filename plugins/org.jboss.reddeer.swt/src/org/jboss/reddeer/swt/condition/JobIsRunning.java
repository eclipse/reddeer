package org.jboss.reddeer.swt.condition;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.Job;
import org.hamcrest.Matcher;

/**
 * Returns true, if there is a non system job running, false 
 * otherwise. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class JobIsRunning implements WaitCondition {
	private Matcher<String>[] ignoreJobs;

	public JobIsRunning() {
	}

	public JobIsRunning(Matcher<String>... ignoreJobs) {
		this.ignoreJobs = ignoreJobs;
	}

	@Override
	public boolean test() {
		return getJobs().size() != 0;
	}

	@Override
	public String description() {
		StringBuilder msg = new StringBuilder("At least one job is running. Currently running jobs: \n");
		for (Job job : getJobs()){
			msg.append(job.getName() + "\n");
		}
		return msg.toString();
	}
	
	private List<Job> getJobs() {
		List<Job> jobs = new ArrayList<Job>();

		for (Job job: Job.getJobManager().find(null)) {
			if (job.isSystem() || job.getState() == Job.SLEEPING)
				continue;

			if (ignoreJobs != null) {
				boolean ignore = false;

				for(Matcher<String> matcher: ignoreJobs) {
					if (matcher.matches(job.getName())) {
						ignore = true;
						break;
					}
				}

				if (ignore)
					continue;
			}

			jobs.add(job);
		}

		return jobs;
	}
}
