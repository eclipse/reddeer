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
package org.jboss.reddeer.junit.extensionpoint;

import java.io.IOException;

/**
 * Eclipse extension for an issue tracker.
 * 
 * @author Andrej Podhradsky
 *
 */
public interface IIssueTracker {

	/**
	 * Decides whether a given issue is supported or not.
	 * 
	 * @param issue
	 *            Issue
	 * @return True if the issue is supported
	 */
	boolean isIssueSupported(Object issue);

	/**
	 * Decides whether a given issue is resolved and ready to test.
	 * 
	 * @param issue
	 *            Issue
	 * @return True if the issue is resolved and ready to test
	 * @throws IOException
	 *             I/O exception
	 */
	boolean isIssueResolved(Object issue) throws IOException;

	/**
	 * Returns a state message of a given issue. This method is used in logging
	 * info.
	 * 
	 * @param issue
	 *            Issue
	 * @return A state message of the issue.
	 * @throws IOException
	 *             I/O exception
	 */
	String getIssueStateMessage(Object issue) throws IOException;
}
