package org.jboss.reddeer.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.Job;
import org.jboss.reddeer.common.logging.Logger;

/**
 * Utils to work with Jobs
 * @author Vlado Pakan
 *
 */
public class Jobs {
    private static final Logger log = Logger.getLogger(Jobs.class);
	public static final int JOB_STATE_NONE = 0;
	public static final int JOB_STATE_SLEEPING = 1;
	public static final int JOB_STATE_WAITING = 2;
	public static final int JOB_STATE_RUNNING = 4;
	public static final String BUILDING_WORKSPACE_JOB = "Building workspace";
	public static final String LOADING_JOB = "Loading";
	public static final String COMPACTING_RESOURCE_MODEL = "Compacting resource model";
	
	public static String getFormattedJobDescription(Job job) {
		StringBuffer sb = new StringBuffer("");
		sb.append("P:\"");
		sb.append(job.getPriority());
		sb.append("\" S:\"");
		sb.append(getStateName(job.getState()));
		sb.append("\" R:\"");
		sb.append(job.getResult());
		sb.append("\" N:\"");
		sb.append(job.getName());
		sb.append("\"");
		return sb.toString();
	}
	
	public static String getStateName(int jobState) {
		String jobStateName = "";

		if (jobState == Jobs.JOB_STATE_NONE)
			jobStateName = "None";
		else if (jobState == Jobs.JOB_STATE_SLEEPING)
			jobStateName = "Sleeping";
		else if (jobState == Jobs.JOB_STATE_WAITING)
			jobStateName = "Waiting";
		else if (jobState == Jobs.JOB_STATE_RUNNING)
			jobStateName = "Running";
		else
			throw new IllegalArgumentException("Unknown JobState");

		return jobStateName;
	}
	
	public static boolean isJobRunning (Job job){
		return (job.getState() == Jobs.JOB_STATE_RUNNING) || (job.getState() == Jobs.JOB_STATE_WAITING);
	}
	
	public static void printAllRunningJobs(){
		log.debug("Print All Running Jobs");
		Job[] jobs = Job.getJobManager().find(null);
		for (Job job : jobs) {
			if (isJobRunning(job)){
				log.info(getFormattedJobDescription(job));
			}

		}
	}
	
    public static void printAllJobs(){
    	log.debug("Print all jobs");
		Job[] jobs = Job.getJobManager().find(null);
		for (Job job : jobs) {
			log.debug(getFormattedJobDescription(job));
		}
	}
    
    public static String[] getAllRunningJobs(){
    	log.debug("Get all running jobs");
    	Job[] jobs = Job.getJobManager().find(null);
    	List<String> jobNames = new ArrayList<String>();
    	for (Job job: jobs){
    		if (isJobRunning(job)){
    			jobNames.add(job.getName());
    		}
    	}
    	return jobNames.toArray(new String[0]);
    }
}
