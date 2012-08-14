package org.jboss.reddeer.eclipse.jface.wizard;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.impl.button.PushButton;

public class WizardDialog {

	protected final Logger log = Logger.getLogger(this.getClass());
	private int currentPage = 0;
	
	public void finish(){
		log.debug("Finish wizard dialog");
		new PushButton("Finish").click();		
	}
	
	public void cancel(){
		log.debug("Cancel wizard dialog");
		new PushButton("Cancel").click();		
	}
	
	public void next(){
		log.debug("Go to next wizard page");
		new PushButton("Next >").click();
		currentPage++;
	}
	
	public void back(){
		log.debug("Go to previous wizard page");
		new PushButton("< Back").click();
		currentPage--;
	}
	
	public void selectPage(int pageIndex){
	  if (pageIndex != currentPage){
	    boolean goBack = pageIndex < currentPage;
	    while (pageIndex != currentPage){
	      if (goBack){
	        back();
	      }
	      else{
	        next();
	      }
	    }
	  }  
	}
	
	public void open(){
	  currentPage = 0;
	}
}
