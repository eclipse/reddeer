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
package org.jboss.reddeer.eclipse.jst.servlet.ui.project.facet;

import org.jboss.reddeer.eclipse.wst.web.ui.wizards.DataModelFacetCreationWizardPage;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.group.DefaultGroup;

/**
 * The first wizard page for creating web project.
 */
public class WebProjectFirstPage extends DataModelFacetCreationWizardPage{
	
	/**
	 * Sets the dynamic web module version.
	 *
	 * @param version the new dynamic web module version
	 */
	public void setDynamicWebModuleVersion(String version){
		new DefaultCombo(new DefaultGroup("Dynamic web module version")).setSelection(version);
	}
	
	/**
	 * Gets the dynamic web module version.
	 *
	 * @return the dynamic web module version
	 */
	public String getDynamicWebModuleVersion(){
		return new DefaultCombo(new DefaultGroup("Dynamic web module version")).getSelection();
	}
	
}
