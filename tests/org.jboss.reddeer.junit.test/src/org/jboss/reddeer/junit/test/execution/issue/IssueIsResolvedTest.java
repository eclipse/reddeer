/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.junit.test.execution.issue;

import static org.junit.Assert.fail;

import org.jboss.reddeer.junit.execution.annotation.RunIf;
import org.jboss.reddeer.junit.execution.impl.IssueIsResolved;
import org.jboss.reddeer.junit.extension.issue.tracker.impl.GitHubIssue;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Andrej Podhradsky
 *
 */
@RunWith(RedDeerWithSkipSuite.class)
public class IssueIsResolvedTest {

	@Test
	@GitHubIssue("repos/jboss-reddeer/reddeer/issues/1286")
	@RunIf(conditionClass = IssueIsResolved.class)
	public void resolvedIssueTest() {
		// this is ok since https://github.com/jboss-reddeer/reddeer/issues/1286 is closed
		RedDeerWithSkipSuite.executeMethod("resolvedIssueTest");
	}

	@Test
	@GitHubIssue("repos/jboss-reddeer/reddeer/issues/620")
	@RunIf(conditionClass = IssueIsResolved.class)
	public void unresolvedIssueTest() {
		RedDeerWithSkipSuite.executeMethod("unresolvedIssueTest");
		fail("This method should be skipped since https://github.com/jboss-reddeer/reddeer/issues/620 is still unresolved");
	}
}
