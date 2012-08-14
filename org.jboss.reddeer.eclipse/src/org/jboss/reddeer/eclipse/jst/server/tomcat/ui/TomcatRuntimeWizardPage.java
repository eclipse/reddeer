package org.jboss.reddeer.eclipse.jst.server.tomcat.ui;

import org.apache.log4j.Logger;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.eclipse.wst.server.RuntimeWizardDialog;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * A wizard page of New server runtime wizard for configuring Tomcat server runtime. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TomcatRuntimeWizardPage extends WizardPage {
	private final Logger log = Logger.getLogger(TomcatRuntimeWizardPage.class);
	
	public TomcatRuntimeWizardPage (RuntimeWizardDialog wizardDialog){
		super (wizardDialog, 1);
	}
	
	public void setInstallationDirectory(String dir){
		show();
		Text text = new LabeledText("Tomcat installation directory:");
		log.debug("Set Tomcat installation directory to: " + dir);
		text.setText(dir);
	}
	
}
