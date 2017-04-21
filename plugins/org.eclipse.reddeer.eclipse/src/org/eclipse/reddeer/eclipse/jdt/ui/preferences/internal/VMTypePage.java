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
package org.eclipse.reddeer.eclipse.jdt.ui.preferences.internal;

import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.list.DefaultList;

/**
 * First page of "Add JRE" wizard accesible from Preferences &gt; Java &gt; Installed
 * JREs.
 * 
 * @author rhopp
 *
 */

public class VMTypePage extends WizardPage {

	/**
	 * Selects desired type.
	 *
	 * @param type the type
	 */
	
	public void selectType(String type){
		new DefaultList().select(type);
	}
	
}
