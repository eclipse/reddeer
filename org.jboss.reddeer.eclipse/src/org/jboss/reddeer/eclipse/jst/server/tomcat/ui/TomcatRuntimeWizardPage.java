package org.jboss.reddeer.eclipse.jst.server.tomcat.ui;

import org.apache.log4j.Logger;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.impl.text.TextWithLabel;

/**
 * A wizard page of New server runtime wizard for configuring Tomcat server runtime. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TomcatRuntimeWizardPage extends WizardPage {
	private final Logger log = Logger.getLogger(TomcatRuntimeWizardPage.class);
	public void setInstallationDirectory(String dir){
		Text text = new TextWithLabel("Tomcat installation directory:");
		log.debug("Set Tomcat installation directory to: " + dir);
		text.setText(dir);
	}
	
}
