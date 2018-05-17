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
package org.eclipse.reddeer.eclipse.core.resources;

import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Represents Java project inside of explorer view.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class JavaProject extends DefaultProject {

	/**
	 * Creates object representing java project.
	 *
	 * @param item the item
	 */
	public JavaProject(TreeItem item) {
		super(item);
	}

	@Override
	public String[] getNatureIds() {
		return new String[] {"org.eclipse.jdt.core.javanature"};
	}
}
