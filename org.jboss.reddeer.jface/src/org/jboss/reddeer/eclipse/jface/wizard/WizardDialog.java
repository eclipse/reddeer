package org.jboss.reddeer.eclipse.jface.wizard;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.condition.AllRunningJobsAreNotActive;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.WaitUntilCondition;
import org.jboss.reddeer.swt.wait.WaitWhileCondition;

public abstract class WizardDialog {

	protected final Logger log = Logger.getLogger(this.getClass());
	
	protected int currentPage = 0;
	
	public abstract WizardPage getFirstPage();
	
	public void finish(){
		log.debug("Finish wizard dialog");

		DefaultShell shell = new DefaultShell();
		new PushButton("Finish").click();
		new WaitWhileCondition(new ShellWithTextIsActive(shell.getText()), 10000);
		new WaitUntilCondition(new AllRunningJobsAreNotActive(), 10000);
	}
	
	public void cancel(){
		log.debug("Cancel wizard dialog");
		DefaultShell shell = new DefaultShell();
		new PushButton("Cancel").click();		
		new WaitWhileCondition(new ShellWithTextIsActive(shell.getText()));
		new WaitUntilCondition(new AllRunningJobsAreNotActive());
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
}
