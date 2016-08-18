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
package org.jboss.reddeer.junit.extension;

/**
 * ExtensionPriority class contains constants of extensions priorities.
 * 
 * @author mlabuda@redhat.com
 * @since 1.2.0
 */
public class ExtensionPriority {

	// Before extensions priorities
	public static final long CLOSE_WELCOME_SCREEN_PRIORITY = 1000000;
	public static final long SET_OPEN_ASSOCIATED_PERSPECTIVE_PRIORITY = 10000;
	public static final long DO_NOT_DOWNLOAD_MAVEN_INDICES_PRIORITY = 1000;
	public static final long BEFORES_LOG_COLLECTOR_PRIORITY = Long.MAX_VALUE;
	
	// After extensions priorities
	public static final long CLOSE_ALL_SHELLS_PRIORITY = -1000000;
	public static final long AFTERSLOG_COLLECTOR_PRIORITY = Long.MIN_VALUE;
}
