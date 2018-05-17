/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.extension;

/**
 * ExtensionPriority class contains constants of extensions priorities.
 * 
 * @author mlabuda@redhat.com
 * @since 1.2.0
 */
public class ExtensionPriority {

	// Before extensions priorities

	public static final long MAXIMIZE_WORKBENCH = 10000000;
	public static final long CLOSE_WELCOME_SCREEN_PRIORITY = 1000000;
	public static final long SET_OPEN_ASSOCIATED_PERSPECTIVE_PRIORITY = 10000;
	public static final long DO_NOT_DOWNLOAD_MAVEN_INDICES_PRIORITY = 1000;
	public static final long BEFORES_LOG_COLLECTOR_PRIORITY = Long.MAX_VALUE;
	
	// After extensions priorities
	public static final long CLOSE_ALL_SHELLS_PRIORITY = -1000000;
	public static final long AFTERSLOG_COLLECTOR_PRIORITY = Long.MIN_VALUE;
}
