/*******************************************************************************
 * Copyright (c) 2019 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Wizard page for creating a java interface.
 * 
 * @author zcervink@redhat.com
 * 
 */
public class NewInterfaceCreationWizardPage extends AbstractJavaWizardPage implements CanImplement {

	/**
	 * Instantiates a new new java interface wizard page.
	 */
	public NewInterfaceCreationWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
}
