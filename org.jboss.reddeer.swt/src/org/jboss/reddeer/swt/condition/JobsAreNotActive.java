package org.jboss.reddeer.swt.condition;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.jobs.Job;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.matcher.TextMatchers;
import org.jboss.reddeer.swt.util.Jobs;
/**
 * Condition is fulfilled when jobs are not active
 * @author Vlado Pakan
 *
 */
public class JobsAreNotActive implements WaitCondition{
	
    private Matcher<String>[] jobMatchers;
    private final Logger log = Logger.getLogger(JobsAreNotActive.class);
    private List<Job> matchedActiveJobs = null;
    
    public JobsAreNotActive(String... jobs){
    	this(new TextMatchers(jobs).getMatchers());
    }
    public JobsAreNotActive(Matcher<String>... jobs){
    	this.jobMatchers = jobs;
    }
	@Override
	public boolean test() {
		matchedActiveJobs = getMatchedActiveJobs();
		if (matchedActiveJobs.size() == 0){
			log.debug("No Jobs to wait for");
			return true;
		}
		else{
			log.debug(description());
			return false;	
		}
		
	}

	public String description() {
		StringBuffer sb = new StringBuffer("Still have to wait for these matched active jobs\n");
		for (Job job : matchedActiveJobs){
			sb.append(Jobs.getFormattedJobDescription(job));
			sb.append("\n");
		}
		return sb.toString();
		
	}
	
	private List<Job> getMatchedActiveJobs(){
		LinkedList<Job> result = new LinkedList<Job>();
		Job[] jobs = Job.getJobManager().find(null);
		
		for (Job job : jobs){
			if (Jobs.isJobRunning(job)){
				boolean matches = false;
				int matcherIndex = 0;
				while (!matches && matcherIndex < jobMatchers.length){
					matches = jobMatchers[matcherIndex].matches(job.getName());
					matcherIndex++;
				}
				if (matches){
					result.add(job);
				}
			}
		}
		
		return result;
	}
}
