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
package org.jboss.reddeer.eclipse.jst.ejb.ui;

import org.jboss.reddeer.eclipse.wst.web.ui.wizards.DataModelFacetCreationWizardPage;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.group.DefaultGroup;

/**
 * Represents EjbProjectFirstPage.
 * @author rawagner
 *
 */
public class EjbProjectFirstPage extends DataModelFacetCreationWizardPage {
	
	/**
	 * Sets Ejb module version.
	 * @param version to set
	 */
	public void setEJBModuleVersion(final String version) {
		new DefaultCombo(new DefaultGroup("EJB module version")).setSelection(version);
	}
	
	/**
	 * Returns current ejb module version.
	 * @return ejb module version
	 */
	public String getEJBModuleVersion() {
		return new DefaultCombo(new DefaultGroup("EJB module version")).getSelection();
	}

}
