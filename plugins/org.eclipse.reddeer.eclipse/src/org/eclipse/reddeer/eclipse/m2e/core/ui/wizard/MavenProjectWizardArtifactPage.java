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
	public void setPackage(String projectPackage){
		new LabeledCombo(new DefaultGroup(referencedComposite, "Artifact"),"Packaging:").setText(projectPackage);
	}
	
	/**
	 * Set project group id.
	 *
	 * @param groupId the new group id
	 */
	public void setGroupId(String groupId){
		new LabeledCombo(new DefaultGroup(referencedComposite, "Artifact"),"Group Id:").setText(groupId);
	}
	
	/**
	 * Set project name.
	 *
	 * @param name the new name
	 */
	public void setName(String name){
		new LabeledCombo(new DefaultGroup(referencedComposite, "Artifact"),"Name:").setText(name);
	}

	/**
	 * Set project description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description){
		new LabeledText(new DefaultGroup(referencedComposite, "Artifact"),"Description:").setText(description);
	}
	
	/**
	 * Set parent project group id.
	 *
	 * @param groupId the new parent group id
	 */
	public void setParentGroupId(String groupId){
		new LabeledCombo(new DefaultGroup(referencedComposite, "Parent Project"),"Group Id:").setText(groupId);
	}

	/**
	 * Set parent project artifact id.
	 *
	 * @param artifactid the new parent artifact id
	 */
	public void setParentArtifactId(String artifactid){
		new LabeledText(new DefaultGroup(referencedComposite, "Parent Project"),"Artifact Id:").setText(artifactid);
	}
	
	/**
	 * Set parent project version.
	 *
	 * @param version the new parent version
	 */
	public void setParentVersion(String version){
		new LabeledText(new DefaultGroup(referencedComposite, "Parent Project"),"Version:").setText(version);
	}

	/**
	 * Set project artifact id.
	 *
	 * @param artifactId the new artifact id
	 */
	public void setArtifactId(String artifactId){
		new LabeledCombo(new DefaultGroup(referencedComposite, "Artifact"),"Artifact Id:").setText(artifactId);
	}
	
	/**
	 * Set project version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version){
		new LabeledCombo(new DefaultGroup(referencedComposite, "Artifact"),"Version:").setText(version);
	}
	
	/**
	 * Get project package.
	 *
	 * @return project package
	 */
	public String getPackage(){
		return new LabeledCombo(new DefaultGroup(referencedComposite, "Artifact"),"Package:").getText();
	}
	
	/**
	 * Get project group id.
	 *
	 * @return project group id
	 */
	public String getGroupId(){
		return new LabeledCombo(new DefaultGroup(referencedComposite, "Artifact"),"Group Id:").getText();
	}
	
	/**
	 * Get project artifact id.
	 *
	 * @return project artifact id
	 */
	public String getArtifactId(){
		return new LabeledCombo(new DefaultGroup(referencedComposite, "Artifact"),"Artifact Id:").getText();
	}
	
	/**
	 * Get project version.
	 *
	 * @return project version
	 */
	public String getVersion(){
		return new LabeledCombo(new DefaultGroup(referencedComposite, "Artifact"),"Version:").getText();
	}

}
