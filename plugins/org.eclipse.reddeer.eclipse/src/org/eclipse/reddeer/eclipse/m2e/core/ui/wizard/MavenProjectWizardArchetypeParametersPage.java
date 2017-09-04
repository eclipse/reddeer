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
package org.eclipse.reddeer.eclipse.m2e.core.ui.wizard;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;

/**
 * Third wizard page for creating maven project
 * @author rawagner
 *
 */
public class MavenProjectWizardArchetypeParametersPage extends WizardPage{
	
	public MavenProjectWizardArchetypeParametersPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Set project package.
	 *
	 * @param projectPackage the new package
	 */
	public MavenProjectWizardArchetypeParametersPage setPackage(String projectPackage){
		new LabeledCombo(this, "Package:").setText(projectPackage);
		return this;
	}
	
	/**
	 * Set project group id.
	 *
	 * @param groupId the new group id
	 */
	public MavenProjectWizardArchetypeParametersPage setGroupId(String groupId){
		new LabeledCombo(this, "Group Id:").setText(groupId);
		return this;
	}

	/**
	 * Set project artifact id.
	 *
	 * @param artifactId the new artifact id
	 */
	public MavenProjectWizardArchetypeParametersPage setArtifactId(String artifactId){
		new LabeledCombo(this, "Artifact Id:").setText(artifactId);
		return this;
	}
	
	/**
	 * Set project version.
	 *
	 * @param version the new version
	 */
	public MavenProjectWizardArchetypeParametersPage setVersion(String version){
		new LabeledCombo(this, "Version:").setText(version);
		return this;
	}
	
	/**
	 * Get project package.
	 *
	 * @return project package
	 */
	public String getPackage(){
		return new LabeledCombo(this, "Package:").getText();
	}
	
	/**
	 * Get project group id.
	 *
	 * @return project group id
	 */
	public String getGroupId(){
		return new LabeledCombo(this, "Group Id:").getText();
	}
	
	/**
	 * Get project artifact id.
	 *
	 * @return project artifact id
	 */
	public String getArtifactId(){
		return new LabeledCombo(this, "Artifact Id:").getText();
	}
	
	/**
	 * Get project version.
	 *
	 * @return project version
	 */
	public String getVersion(){
		return new LabeledCombo(this, "Version:").getText();
	}


}
