package org.jboss.reddeer.eclipse.jst.servlet.ui;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * The third wizard page for creating web project.
 */
public class WebProjectThirdPage extends WizardPage{

	public void setContextRoot(String contextRoot){
		new LabeledText("Context root:").setText(contextRoot);
	}
	
	public String getContextRoot(){
		return new LabeledText("Context root:").getText();
	}
	
	public void setContentDirectory(String contentDirectory){
		new LabeledText("Content directory:").setText(contentDirectory);
	}
	
	public String getContentDirectory(){
		return new LabeledText("Content directory:").getText();
	}
	
	public void setGenerateWebXmlDeploymentDescriptor(boolean generate){
		new CheckBox("Generate web.xml deployment descriptor").toggle(generate);
	}
	
	public boolean isGenerateWebXmlDeploymentDescriptor(){
		return new CheckBox("Generate web.xml deployment descriptor").isChecked();
	}
	
	
	
}

