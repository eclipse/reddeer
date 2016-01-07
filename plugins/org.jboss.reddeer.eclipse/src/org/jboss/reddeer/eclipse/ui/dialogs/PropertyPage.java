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
package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.jface.preference.PreferencePage;

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
