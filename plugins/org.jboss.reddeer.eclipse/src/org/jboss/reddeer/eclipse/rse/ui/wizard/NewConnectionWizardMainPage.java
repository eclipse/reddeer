package org.jboss.reddeer.eclipse.rse.ui.wizard;

import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.text.DefaultText;


/**
 * This class represents the main wizard page of the New Connection wizard dialog where
 * the host name and connection name of the remote system are set.
 * @author Pavol Srna
 *
 */
public class NewConnectionWizardMainPage extends WizardPage {

	/**
	 * Set Host name
	 * @param hostname
	 */
	public void setHostName(String hostname){
		getHostNameCombo().setText(hostname);
	}

	/**
	 * Set Connection name
	 * @param String name
	 */
	public void setConnectionName(String name){
		new DefaultText(0).setText(name);
	}
	
	/**
	 * Return list of all defined host names
	 * @return List<String> hostnames
	 */
	public List<String> getHostNames() {
		List<String> items = new LinkedList<String>(getHostNameCombo().getItems());
		return items;
	}
	
	private Combo getHostNameCombo(){
		return new DefaultCombo(1);
	}	
}