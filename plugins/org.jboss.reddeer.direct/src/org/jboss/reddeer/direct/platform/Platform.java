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
package org.jboss.reddeer.direct.platform;

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
