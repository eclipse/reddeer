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
package org.eclipse.reddeer.eclipse.jdt.ui.packageview;

import org.eclipse.reddeer.eclipse.ui.navigator.resources.AbstractExplorer;

/**
 * Represents Package Explorer in Eclipse
 * 
 * @author Vlado Pakan
 *
 */
public class PackageExplorerPart extends AbstractExplorer {
    
	/**
	 * Constructs the view with Package Explorer.
	 */
	public PackageExplorerPart() {
		super("Package Explorer");
	}

}
