package org.jboss.reddeer.eclipse.jst.servlet.ui;

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
