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
package org.eclipse.reddeer.eclipse.m2e.scm.wizards;

import java.util.List;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

public class MavenCheckoutLocationPage extends WizardPage{
	
	public MavenCheckoutLocationPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Get available SCM types.
	 *
	 * @return list of available SCM types
	 */
	public List<String> getAvailableSCMTypes(){
		return new LabeledCombo(referencedComposite, "SCM URL:").getItems();
	}
	
	/**
	 * Get selected SCM type.
	 *
	 * @return selected SCM type
	 */
	public String getSelectedSCMType(){
		return new LabeledCombo(referencedComposite, "SCM URL:").getSelection();
	}
	
	/**
	 * Set SCM type.
	 *
	 * @param scmType to select
	 */
	public void setSCMType(String scmType){
		new LabeledCombo(referencedComposite, "SCM URL:").setSelection(scmType);
	}
	
	/**
	 * Set SCM URL.
	 *
	 * @param scmURL to select
	 */
	public void setSCMURL(String scmURL){
		new DefaultCombo(referencedComposite, 1).setText(scmURL);
	}
	
	/**
	 * Get selected SCM URL.
	 *
	 * @return selected SCM URL
	 */
	public String getSCMURL(){
		return new DefaultCombo(referencedComposite, 1).getText();
	}
	
	/**
	 * Get available SCM URLs.
	 *
	 * @return list of available SCM URLs
	 */
	public List<String> getAvailableSCMURLs(){
		return new DefaultCombo(referencedComposite, 1).getItems();
	}
	
	/**
	 * Toggle checkout head revision .
	 *
	 * @param toggle the toggle
	 */
	public void toggleCheckoutHeadRevision(boolean toggle){
		 new CheckBox(referencedComposite, "Check out Head Revision").toggle(toggle);
	}
	
	/**
	 * Check if checkout head revision is checked.
	 *
	 * @return true if checkout head revision is checked, false otherwise
	 */
	public boolean isCheckoutHeadRevision(){
		return new CheckBox(referencedComposite, "Check out Head Revision").isChecked();
	}

	/**
	 * Set revision.
	 *
	 * @param revision to set
	 */
	public void setRevision(String revision){
		new LabeledText(referencedComposite, "Revision:").setText(revision);
	}
	
	/**
	 * Toggle checkout all projects.
	 *
	 * @param toggle the toggle
	 */
	public void toggleCheckoutAllProjects(boolean toggle){
		new CheckBox(referencedComposite, "Check out All Projects").toggle(toggle);
	}
	
	/**
	 * Check if checkout all projects is checked.
	 *
	 * @return true if checkout all projects is checked, false otherwise
	 */
	public boolean isCheckoutAllProjects(){
		return new CheckBox(referencedComposite, "Check out All Projects").isChecked();
	}
}
