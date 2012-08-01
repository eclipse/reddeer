package org.jboss.reddeer.eclipse.jface.wizard;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.reddeer.swt.impl.button.PushButton;

public abstract class WizardPage {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	public void finish(){
		logger.debug("Finish wizard page");
		new PushButton("Finish").click();		
	}
	
	public void cancel(){
		logger.debug("Cancel wizard page");
		new PushButton("Cancel").click();		
	}
	
	public void next(){
		logger.debug("Go to next wizard page");
		new PushButton("Next >").click();
	}
	
	public void back(){
		logger.debug("Go to previous wizard page");
		new PushButton("< Back").click();
	}
}
