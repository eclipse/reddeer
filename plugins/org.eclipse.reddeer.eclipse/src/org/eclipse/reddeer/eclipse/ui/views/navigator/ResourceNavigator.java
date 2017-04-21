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
package org.eclipse.reddeer.eclipse.ui.views.navigator;

import org.eclipse.reddeer.eclipse.ui.navigator.resources.AbstractExplorer;

/**
 * Navigator RedDeer implementation
 * "The Navigator view provides a hierarchical view of the resources
 * in the Workbench including hidden files."
 * 
 * @author Radoslav Rabara
 *
 */
public class ResourceNavigator extends AbstractExplorer {
	
	/**
	 * Construct the explorer with "Navigator". 
	 */
	public ResourceNavigator() {
		super("Navigator");
	}
}
