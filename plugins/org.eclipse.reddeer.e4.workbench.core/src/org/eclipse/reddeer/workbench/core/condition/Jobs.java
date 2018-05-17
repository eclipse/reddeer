/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.core.condition;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.reddeer.common.logging.Logger;

/**
 * Jobs provides utilities to work with Eclipse jobs.
 * 
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
	
	/**
	 * Gets formatted job description containing information about priority, state, result and name.
	 * 
	 * @param job job to get its formatted description.
	 * @return formatted job description
	 */
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
	
	/**
	 * Gets state name as a string instead of internal int representation inside Eclipse.
	 * 
	 * @param jobState internal code of job state.
	 * @return job state name
	 */
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
	
	/**
	 * Finds out whether specified job is running or not.
	 * 
	 * @param job job to find out its state
	 * @return true if job is running. false otherwise.
	 */
	public static boolean isJobRunning (Job job){
		return (job.getState() == Jobs.JOB_STATE_RUNNING) || (job.getState() == Jobs.JOB_STATE_WAITING);
	}
	
	/**
	 * Prints all running jobs via logger on info level.
	 */
	public static void printAllRunningJobs(){
		log.debug("Print All Running Jobs");
		Job[] jobs = Job.getJobManager().find(null);
		for (Job job : jobs) {
			if (isJobRunning(job)){
				log.info(getFormattedJobDescription(job));
			}

		}
	}
	
	/**
	 * Prints all jobs via logger on info level. 
	 */
    public static void printAllJobs(){
    	log.debug("Print all jobs");
		Job[] jobs = Job.getJobManager().find(null);
		for (Job job : jobs) {
			log.debug(getFormattedJobDescription(job));
		}
	}
    
    /**
     * Gets names of all running jobs.
     *  
     * @return names of all running jobs
     */
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
