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
package org.eclipse.reddeer.eclipse.jst.j2ee.ui.project.facet;

import org.eclipse.reddeer.eclipse.wst.web.ui.wizards.DataModelFacetCreationWizardPage;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.group.DefaultGroup;

/**
 * The first Wizard page for creating EAR project.
 */
public class EarProjectFirstPage extends DataModelFacetCreationWizardPage{
	
	public EarProjectFirstPage(WizardDialog dialog) {
		super(dialog);
	}
	
	/**
	 * Sets EAR verion.
	 * 
	 * @param version EAR version
	 */
	public void setEARVersion(String version){
		new DefaultCombo(new DefaultGroup(referencedComposite, "EAR version")).setSelection(version);
	}
	
	/**
	 * Returns EAR version.
	 * 
	 * @return EAR version
	 */
	public String getEARVersion(){
		return new DefaultCombo(new DefaultGroup(referencedComposite, "EAR version")).getSelection();
	}
	


}
