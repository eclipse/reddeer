package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.swt.util.Jobs;

public class AllRunningJobsAreNotActive extends JobsAreNotActive{
	
	public AllRunningJobsAreNotActive() {
		super(Jobs.getAllRunningJobs());
	}
}
