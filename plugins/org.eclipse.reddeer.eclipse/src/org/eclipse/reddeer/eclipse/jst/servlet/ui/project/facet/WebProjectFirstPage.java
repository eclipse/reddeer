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
package org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet;

import org.eclipse.reddeer.eclipse.wst.web.ui.wizards.DataModelFacetCreationWizardPage;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.group.DefaultGroup;

/**
 * The first wizard page for creating web project.
 */
public class WebProjectFirstPage extends DataModelFacetCreationWizardPage{
	
	public WebProjectFirstPage(WizardDialog dialog) {
		super(dialog);
	}
	
	/**
	 * Sets the dynamic web module version.
	 *
	 * @param version the new dynamic web module version
	 */
	public void setDynamicWebModuleVersion(String version){
		new DefaultCombo(new DefaultGroup(referencedComposite, "Dynamic web module version")).setSelection(version);
	}
	
	/**
	 * Gets the dynamic web module version.
	 *
	 * @return the dynamic web module version
	 */
	public String getDynamicWebModuleVersion(){
		return new DefaultCombo(new DefaultGroup(referencedComposite, "Dynamic web module version")).getSelection();
	}
	
}
