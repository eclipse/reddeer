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
package org.eclipse.reddeer.eclipse.jst.ejb.ui.project.facet;

import org.eclipse.reddeer.eclipse.wst.web.ui.wizards.DataModelFacetCreationWizardPage;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.group.DefaultGroup;

/**
 * Represents EjbProjectFirstPage.
 * @author rawagner
 *
 */
public class EjbProjectFirstPage extends DataModelFacetCreationWizardPage {
	
	public EjbProjectFirstPage(WizardDialog dialog) {
		super(dialog);
	}
	
	/**
	 * Sets Ejb module version.
	 * @param version to set
	 */
	public void setEJBModuleVersion(final String version) {
		new DefaultCombo(new DefaultGroup(referencedComposite, "EJB module version")).setSelection(version);
	}
	
	/**
	 * Returns current ejb module version.
	 * @return ejb module version
	 */
	public String getEJBModuleVersion() {
		return new DefaultCombo(new DefaultGroup(referencedComposite, "EJB module version")).getSelection();
	}

}
