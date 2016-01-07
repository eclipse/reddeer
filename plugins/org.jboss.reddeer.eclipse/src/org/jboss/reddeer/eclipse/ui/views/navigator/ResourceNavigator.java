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
package org.jboss.reddeer.eclipse.ui.views.navigator;

import org.jboss.reddeer.eclipse.jdt.ui.AbstractExplorer;

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
