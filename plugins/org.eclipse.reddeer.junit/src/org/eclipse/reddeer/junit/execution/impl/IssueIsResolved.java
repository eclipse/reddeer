/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.execution.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.execution.TestMethodShouldRun;
import org.eclipse.reddeer.junit.extensionpoint.IIssueTracker;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.runners.model.FrameworkMethod;

/**
 * This implementation uses eclipse extensions of {@link IIssueTracker} to
 * decide whether all issues are resolved and ready to test. If not then the
 * test method is skipped.
 * <p>
 * <b>NOTE:</b> This feature is disabled by default. You can enable it by
 * setting the system property {@value #SKIP_UNRESILVED_ISSUES} to true.
 * 
 * @author Andrej Podhradsky
 * 
 * @see IIssueTracker
 *
 */
public class IssueIsResolved implements TestMethodShouldRun {

	public static final String SKIP_UNRESILVED_ISSUES = "rd.skipUnresolvedIssues";

	private Logger log = new Logger(IssueIsResolved.class);

	@Override
	public boolean shouldRun(FrameworkMethod method) {
		boolean skipUnresolvedIssues = Boolean.valueOf(System.getProperty(SKIP_UNRESILVED_ISSUES, "false"));
		if (!skipUnresolvedIssues) {
			return true;
		}
		List<IIssueTracker> issueTrackers = RedDeerSuite.getIssueTrackerExtensions();
		for (Annotation annotation : method.getMethod().getDeclaredAnnotations()) {
			for (IIssueTracker issueTracker : issueTrackers) {
				try {
					if (issueTracker.isIssueSupported(annotation) && !issueTracker.isIssueResolved(annotation)) {
						log.info(issueTracker.getIssueStateMessage(annotation) + ", skipping test '" + method.getName()
								+ "'");
						return false;
					}
				} catch (IOException e) {
					e.printStackTrace();
					log.warn("I/O exception occured when getting information about issue " + annotation);
				}
			}
		}
		return true;
	}

}
