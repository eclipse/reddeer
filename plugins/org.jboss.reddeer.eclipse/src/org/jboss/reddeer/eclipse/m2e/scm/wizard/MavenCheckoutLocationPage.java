package org.jboss.reddeer.eclipse.m2e.scm.wizard;

import java.util.List;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.text.LabeledText;

public class MavenCheckoutLocationPage extends WizardPage{
	
	/**
	 * Get available SCM types
	 * @return list of available SCM types
	 */
	public List<String> getAvailableSCMTypes(){
		return new LabeledCombo("SCM URL:").getItems();
	}
	
	/**
	 * Get selected SCM type
	 * @return selected SCM type
	 */
	public String getSelectedSCMType(){
		return new LabeledCombo("SCM URL:").getSelection();
	}
	
	/**
	 * Set SCM type
	 * @param scmType to select
	 */
	public void setSCMType(String scmType){
		new LabeledCombo("SCM URL:").setSelection(scmType);
	}
	
	/**
	 * Set SCM URL
	 * @param scmURL to select
	 */
	public void setSCMURL(String scmURL){
		new DefaultCombo(1).setText(scmURL);
	}
	
	/**
	 * Get selected SCM URL
	 * @return selected SCM URL
	 */
	public String getSCMURL(){
		return new DefaultCombo(1).getText();
	}
	
	/**
	 * Get available SCM URLs
	 * @return list of available SCM URLs
	 */
	public List<String> getAvailableSCMURLs(){
		return new DefaultCombo(1).getItems();
	}
	
	/**
	 * Toggle checkout head revision 
	 * @param toggle
	 */
	public void toggleCheckoutHeadRevision(boolean toggle){
		 new CheckBox("Check out Head Revision").toggle(toggle);
	}
	
	/**
	 * Check if checkout head revision is checked
	 * @return true if checkout head revision is checked, false otherwise
	 */
	public boolean isCheckoutHeadRevision(){
		return new CheckBox("Check out Head Revision").isChecked();
	}

	/**
	 * Set revision
	 * @param revision to set
	 */
	public void setRevision(String revision){
		new LabeledText("Revision:").setText(revision);
	}
	
	/**
	 * Toggle checkout all projects
	 * @param toggle
	 */
	public void toggleCheckoutAllProjects(boolean toggle){
		new CheckBox("Check out All Projects").toggle(toggle);
	}
	
	/**
	 * Check if checkout all projects is checked
	 * @return true if checkout all projects is checked, false otherwise
	 */
	public boolean isCheckoutAllProjects(){
		return new CheckBox("Check out All Projects").isChecked();
	}
}
