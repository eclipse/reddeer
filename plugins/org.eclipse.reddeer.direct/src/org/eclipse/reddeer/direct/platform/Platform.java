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
package org.eclipse.reddeer.direct.platform;

import java.io.File;

/**
 * This class provides support for Eclipse Platform.
 * 
 * @author mlabuda@redhat.com
 * @since 1.2.0
 */
public class Platform {

	/**
	 * Gets Eclipse workbench log file located at workspace .metadata directory.
	 * 
	 * @return eclipse log file
	 */
	public static File getWorkbenchLog() {
		return org.eclipse.core.runtime.Platform.getLogFileLocation().toFile();
	}
}
