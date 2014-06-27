package org.jboss.reddeer.swt.condition;

import org.eclipse.core.runtime.jobs.Job;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;

/**
 * Condition is met when there is/are non-system running job(s).
 * List of tested jobs can be filtered using matchers.
 * 
 * @author Lucia Jelinkova
 */
@SuppressWarnings("rawtypes")
public class JobIsRunning implements WaitCondition {
	private Logger log = Logger.getLogger(JobIsRunning.class);

	private Matcher[] consideredJobs;
	private Matcher[] excludeJobs;

	/**
	 * Constructs JobIsRunning wait condition. Condition is met when job is running.
	 */
	public JobIsRunning() {
		this(null, null);
	}

	/**
	 * Constructs JobIsRunning wait condition. Condition is met when job(s) is/are running.
	 * Test only jobs matching the specified matchers.
	 * 
	 * @param consideredJobs If not <code>null</code>, only jobs whose name matches
	 * any of these matchers will be tested. Use in case you want to make sure all
	 * jobs from a limited set are not running, and you don't care about the rest
	 * of jobs.
	 */
	public JobIsRunning(Matcher[] consideredJobs) {
		this(consideredJobs, null);
	}

	/**
	 * Constructs JobIsRunning wait condition. Condition is met when job(s) is/are running.
	 * Test only jobs matching the specified matchers which are not excluded by 
	 * another specified matchers.
	 * 
	 * @param consideredJobs If not <code>null</code>, only jobs whose name matches
	 * any of these matchers will be tested. Use in case you want to make sure all
	 * jobs from a limited set are not running, and you don't care about the rest
	 * of jobs.
	 * @param excludeJobs If not <code>null</code>, jobs whose name matches any of
	 * these matcher will be ignored. Use in case you don't care about limited set
	 * of jobs. These matchers will overrule <code>consideredJobs</code> results,
	 * job matched by both <code>consideredJobs</code> and <code>excludeJobs</code>
	 * will be excluded.
	 */
	public JobIsRunning(Matcher[] consideredJobs, Matcher[] excludeJobs) {
		this.consideredJobs = consideredJobs;
		this.excludeJobs = excludeJobs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean test() {
		log.debug("test:");

		for (Job job: Job.getJobManager().find(null)) {
			if (excludeJobs != null && CoreMatchers.anyOf(excludeJobs).matches(job.getName())) {
				log.debug("  job '%s' specified by excludeJobs matchers, skipped", job.getName());
				continue;
			}

			if (consideredJobs != null && !CoreMatchers.anyOf(consideredJobs).matches(job.getName())) {
				log.debug("  job '%s' is not listed in considered jobs, ignore it", job.getName());
				continue;
			}

			if (job.isSystem() || job.getState() == Job.SLEEPING) {
				log.debug("  job '%s' is system job or not running, skipped", job.getName());
				continue;
			}

			/* there's no reason why this one should be ignored, lets wait... */
			log.debug("  job '%s' has no excuses, wait for it", job.getName());
			return true;
		}

		return false;
	}

	@Override
	public String description() {
		return "at least one job is running";
	}
}
