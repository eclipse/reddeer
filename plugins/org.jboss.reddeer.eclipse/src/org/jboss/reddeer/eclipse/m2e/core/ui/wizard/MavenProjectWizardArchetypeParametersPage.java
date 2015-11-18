package org.jboss.reddeer.eclipse.m2e.core.ui.wizard;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;

/**
 * Third wizard page for creating maven project
 * @author rawagner
 *
 */
public class MavenProjectWizardArchetypeParametersPage extends WizardPage{
	
	/**
	 * Set project package.
	 *
	 * @param projectPackage the new package
	 */
	public void setPackage(String projectPackage){
		new LabeledCombo("Package:").setText(projectPackage);
	}
	
	/**
	 * Set project group id.
	 *
	 * @param groupId the new group id
	 */
	public void setGroupId(String groupId){
		new LabeledCombo("Group Id:").setText(groupId);
	}

	/**
	 * Set project artifact id.
	 *
	 * @param artifactId the new artifact id
	 */
	public void setArtifactId(String artifactId){
		new LabeledCombo("Artifact Id:").setText(artifactId);
	}
	
	/**
	 * Set project version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version){
		new LabeledCombo("Version:").setText(version);
	}
	
	/**
	 * Get project package.
	 *
	 * @return project package
	 */
	public String getPackage(){
		return new LabeledCombo("Package:").getText();
	}
	
	/**
	 * Get project group id.
	 *
	 * @return project group id
	 */
	public String getGroupId(){
		return new LabeledCombo("Group Id:").getText();
	}
	
	/**
	 * Get project artifact id.
	 *
	 * @return project artifact id
	 */
	public String getArtifactId(){
		return new LabeledCombo("Artifact Id:").getText();
	}
	
	/**
	 * Get project version.
	 *
	 * @return project version
	 */
	public String getVersion(){
		return new LabeledCombo("Version:").getText();
	}


}
