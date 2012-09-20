package org.jboss.reddeer.eclipse.wst.server.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.Job;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * Returns true, if there is a non system job running, false 
 * otherwise. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class NonSystemJobRunsCondition implements WaitCondition {

	@Override
	public boolean test() {
		return getJobs().size() != 0;
	}
	
	@Override
	public String description() {
		StringBuilder msg = new StringBuilder("Expected no running jobs, found the following jobs: \n");
		for (Job job : getJobs()){
			msg.append(job.getName() + "\n");
		}
		return msg.toString();
	}
	
	private List<Job> getJobs(){
		List<Job> jobs = new ArrayList<Job>();
		for (Job job : Job.getJobManager().find(null)){
			if (!job.isSystem() && Job.SLEEPING != job.getState()){
				jobs.add(job);
			}
		}
		return jobs;
	}
}
