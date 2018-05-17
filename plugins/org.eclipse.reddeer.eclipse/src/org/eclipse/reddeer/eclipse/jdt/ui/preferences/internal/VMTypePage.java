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
package org.eclipse.reddeer.eclipse.jdt.ui.preferences.internal;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
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
	
	public VMTypePage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Selects desired type.
	 *
	 * @param type the type
	 */
	
	public VMTypePage selectType(String type){
		new DefaultList(this).select(type);
		return this;
	}
	
}
