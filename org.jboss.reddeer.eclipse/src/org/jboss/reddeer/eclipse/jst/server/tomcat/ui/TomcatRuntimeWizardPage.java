package org.jboss.reddeer.eclipse.jst.server.tomcat.ui;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private final Log logger = LogFactory.getLog(TomcatRuntimeWizardPage.class);
	public void setInstallationDirectory(String dir){
		Text text = new TextWithLabel("Tomcat installation directory:");
		logger.debug("Set Tomcat installation directory to: " + dir);
		text.setText(dir);
	
	}
}
