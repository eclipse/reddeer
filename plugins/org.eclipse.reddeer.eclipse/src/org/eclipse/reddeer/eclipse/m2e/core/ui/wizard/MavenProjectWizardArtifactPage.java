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
package org.eclipse.reddeer.eclipse.m2e.core.ui.wizard;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.group.DefaultGroup;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Represents MavenProjectWizardArtifactPage
 * @author rawagner
 *
 */
public class MavenProjectWizardArtifactPage extends WizardPage{
	
	public MavenProjectWizardArtifactPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Set project package.
	 *
	 * @param projectPackage the new package
	 */
	public MavenProjectWizardArtifactPage setPackage(String projectPackage){
		new LabeledCombo(new DefaultGroup(this, "Artifact"),"Packaging:").setText(projectPackage);
		return this;
	}
	
	/**
	 * Set project group id.
	 *
	 * @param groupId the new group id
	 */
	public MavenProjectWizardArtifactPage setGroupId(String groupId){
		new LabeledCombo(new DefaultGroup(this, "Artifact"),"Group Id:").setText(groupId);
		return this;
	}
	
	/**
	 * Set project name.
	 *
	 * @param name the new name
	 */
	public MavenProjectWizardArtifactPage setName(String name){
		new LabeledCombo(new DefaultGroup(this, "Artifact"),"Name:").setText(name);
		return this;
	}

	/**
	 * Set project description.
	 *
	 * @param description the new description
	 */
	public MavenProjectWizardArtifactPage setDescription(String description){
		new LabeledText(new DefaultGroup(this, "Artifact"),"Description:").setText(description);
		return this;
	}
	
	/**
	 * Set parent project group id.
	 *
	 * @param groupId the new parent group id
	 */
	public MavenProjectWizardArtifactPage setParentGroupId(String groupId){
		new LabeledCombo(new DefaultGroup(this, "Parent Project"),"Group Id:").setText(groupId);
		return this;
	}

	/**
	 * Set parent project artifact id.
	 *
	 * @param artifactid the new parent artifact id
	 */
	public MavenProjectWizardArtifactPage setParentArtifactId(String artifactid){
		new LabeledText(new DefaultGroup(this, "Parent Project"),"Artifact Id:").setText(artifactid);
		return this;
	}
	
	/**
	 * Set parent project version.
	 *
	 * @param version the new parent version
	 */
	public MavenProjectWizardArtifactPage setParentVersion(String version){
		new LabeledText(new DefaultGroup(this, "Parent Project"),"Version:").setText(version);
		return this;
	}

	/**
	 * Set project artifact id.
	 *
	 * @param artifactId the new artifact id
	 */
	public MavenProjectWizardArtifactPage setArtifactId(String artifactId){
		new LabeledCombo(new DefaultGroup(this, "Artifact"),"Artifact Id:").setText(artifactId);
		return this;
	}
	
	/**
	 * Set project version.
	 *
	 * @param version the new version
	 */
	public MavenProjectWizardArtifactPage setVersion(String version){
		new LabeledCombo(new DefaultGroup(this, "Artifact"),"Version:").setText(version);
		return this;
	}
	
	/**
	 * Get project package.
	 *
	 * @return project package
	 */
	public String getPackage(){
		return new LabeledCombo(new DefaultGroup(this, "Artifact"),"Package:").getText();
	}
	
	/**
	 * Get project group id.
	 *
	 * @return project group id
	 */
	public String getGroupId(){
		return new LabeledCombo(new DefaultGroup(this, "Artifact"),"Group Id:").getText();
	}
	
	/**
	 * Get project artifact id.
	 *
	 * @return project artifact id
	 */
	public String getArtifactId(){
		return new LabeledCombo(new DefaultGroup(this, "Artifact"),"Artifact Id:").getText();
	}
	
	/**
	 * Get project version.
	 *
	 * @return project version
	 */
	public String getVersion(){
		return new LabeledCombo(new DefaultGroup(this, "Artifact"),"Version:").getText();
	}

}
