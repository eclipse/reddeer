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
