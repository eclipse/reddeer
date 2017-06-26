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
