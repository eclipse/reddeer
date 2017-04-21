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
package org.eclipse.reddeer.eclipse.ui.dialogs;

import org.eclipse.reddeer.jface.preference.PreferencePage;

/**
 * Represents a general property page.
 * 
 * @author Lucia Jelinkova
 * 
 */
public abstract class PropertyPage extends PreferencePage {

	/**
	 * Instantiates a new property page.
	 *
	 * @param path the path
	 */
	public PropertyPage(String... path) {
		super(path);
	}
}
