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
package org.eclipse.reddeer.direct.workspace;

import org.eclipse.core.resources.ResourcesPlugin;

/**
 * This class provides support for Eclipse workspace.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class Workspace {

	/**
	 * Returns an absolute path of current workspace.
	 * 
	 * @return absolute path of current workspace
	 */
	public static String getLocation() {
		return ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
	}

}
