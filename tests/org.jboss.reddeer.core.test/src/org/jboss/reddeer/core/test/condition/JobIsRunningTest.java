/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.reddeer.core.test.condition;

import static org.junit.Assert.fail;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for JobIsRunning condition.
 * 
 * @author Jan Novak <jnovak@redhat.com>
 */
@RunWith(RedDeerSuite.class)
public class JobIsRunningTest extends JobTestBase {
	
	private static final String TEST_JOB_PREFIX = JobIsRunningTest.class + "_TEST_JOB_";
	private static final String TEST_JOB_1 = TEST_JOB_PREFIX + 1;
	private static final String TEST_JOB_2 = TEST_JOB_PREFIX + 2;
	private static final String TEST_JOB_3 = TEST_JOB_PREFIX + 3;
	private static final String TEST_JOB_4 = TEST_JOB_PREFIX + 4;

	@Test
	public void testSimple() {
		String job = TEST_JOB_1;
		runParallelyForSeconds(job, 2);
		new WaitWhile(new JobIsRunning());
		assertNotRunning(job);
	}

	@Test
	public void testConsidered() {
		String consideredOne = TEST_JOB_1;
		String consideredTwo = TEST_JOB_2;
		String notConsidered = TEST_JOB_3;
		
		runParallelyForSeconds(consideredOne, 1);
		runParallelyForSeconds(consideredTwo, 2);
		runParallelyForSeconds(notConsidered, 4);
		
		new WaitWhile(new JobIsRunning(
				new Matcher[]{CoreMatchers.is(consideredOne), CoreMatchers.is(consideredTwo)}
		));
		
		assertNotRunning(consideredOne);
		assertNotRunning(consideredTwo);
		assertRunning(notConsidered);
	}
	
	@Test
	public void testConsideredExcluded() {
		String considered = TEST_JOB_1;
		String excluded = TEST_JOB_2;

		runParallelyForSeconds(considered, 6);
		runParallelyForSeconds(excluded, 30);

		new WaitWhile(new JobIsRunning(
				new Matcher[]{CoreMatchers.is(considered)}, 
				new Matcher[]{CoreMatchers.is(excluded)}
		));

		assertNotRunning(considered);
		assertRunning(excluded);
	}

	@Test
	public void testIgnoredSystemJob() {
		testSystemJob(true);
	}

	@Test
	public void testNotIgnoredSystemJob() {
		testSystemJob(false);
	}

	private void testSystemJob(boolean ignoreSystemJobs) {
		String excludedShort = TEST_JOB_1;
		String considered = TEST_JOB_2;
		String system = TEST_JOB_3;
		String excludedLong = TEST_JOB_4;

		runParallelyForSeconds(excludedShort, 3);
		runParallelyForSeconds(considered, 6);
		runParallelyForSecondsAsSystem(system, 9);
		runParallelyForSeconds(excludedLong, 30);

		new WaitWhile(new JobIsRunning(
				new Matcher[]{CoreMatchers.is(considered), CoreMatchers.is(system)},
				new Matcher[]{CoreMatchers.is(excludedShort), CoreMatchers.is(excludedLong)}, 
				ignoreSystemJobs
		));

		assertNotRunning(excludedShort);
		assertNotRunning(considered);
		assertRunning(excludedLong);

		if (ignoreSystemJobs) {
			assertRunning(system);
		} else {
			assertNotRunning(system);
		}
	}

	@Test(expected = WaitTimeoutExpiredException.class)
	public void testShorterTimeOutThanJobDuration() {
		runParallelyForSeconds(TEST_JOB_1, 6);
		new WaitWhile(new JobIsRunning(), TimePeriod.getCustom(4));
		fail("No exception was thrown!");
	}

}
