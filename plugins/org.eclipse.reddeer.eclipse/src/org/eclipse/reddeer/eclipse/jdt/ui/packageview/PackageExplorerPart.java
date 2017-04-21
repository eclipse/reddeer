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
