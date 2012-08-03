package org.jboss.reddeer.eclipse.jface.wizard;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.impl.button.PushButton;

public abstract class WizardPage {

	protected final Logger log = Logger.getLogger(this.getClass());
	
	public void finish(){
		log.debug("Finish wizard page");
		new PushButton("Finish").click();		
	}
	
	public void cancel(){
		log.debug("Cancel wizard page");
		new PushButton("Cancel").click();		
	}
	
	public void next(){
		log.debug("Go to next wizard page");
		new PushButton("Next >").click();
	}
	
	public void back(){
		log.debug("Go to previous wizard page");
		new PushButton("< Back").click();
	}
}
