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

import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * The third wizard page for creating web project.
 */
public class WebProjectThirdPage extends WizardPage{

	/**
	 * Sets the context root.
	 *
	 * @param contextRoot the new context root
	 */
	public void setContextRoot(String contextRoot){
		new LabeledText("Context root:").setText(contextRoot);
	}
	
	/**
	 * Gets the context root.
	 *
	 * @return the context root
	 */
	public String getContextRoot(){
		return new LabeledText("Context root:").getText();
	}
	
	/**
	 * Sets the content directory.
	 *
	 * @param contentDirectory the new content directory
	 */
	public void setContentDirectory(String contentDirectory){
		new LabeledText("Content directory:").setText(contentDirectory);
	}
	
	/**
	 * Gets the content directory.
	 *
	 * @return the content directory
	 */
	public String getContentDirectory(){
		return new LabeledText("Content directory:").getText();
	}
	
	/**
	 * Sets the generate web xml deployment descriptor.
	 *
	 * @param generate the new generate web xml deployment descriptor
	 */
	public void setGenerateWebXmlDeploymentDescriptor(boolean generate){
		new CheckBox("Generate web.xml deployment descriptor").toggle(generate);
	}
	
	/**
	 * Checks if is generate web xml deployment descriptor.
	 *
	 * @return true, if is generate web xml deployment descriptor
	 */
	public boolean isGenerateWebXmlDeploymentDescriptor(){
		return new CheckBox("Generate web.xml deployment descriptor").isChecked();
	}
	
	
	
}

