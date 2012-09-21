package org.jboss.reddeer.eclipse.jface.wizard;

import org.apache.log4j.Logger;
import org.jboss.reddeer.eclipse.jface.exception.JFaceLayerException;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * A dialog where are wizard pages displayed. It can operate Next, Back, Cancel and Finish buttons. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class WizardDialog {

	protected final Logger log = Logger.getLogger(this.getClass());
	
	protected int currentPage = 0;
	
	public abstract WizardPage getFirstPage();
	
	public void finish(){
		log.debug("Finish wizard dialog");

		DefaultShell shell = new DefaultShell();
		Button button = new PushButton("Finish");
		checkButtonEnabled(button);
		button.click();
		
		new WaitWhile(new ShellWithTextIsActive(shell.getText()), TimePeriod.LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}
	
	public void cancel(){
		log.debug("Cancel wizard dialog");
		
		DefaultShell shell = new DefaultShell();
		new PushButton("Cancel").click();		
		
		new WaitWhile(new ShellWithTextIsActive(shell.getText()));
		new WaitWhile(new JobIsRunning());
	}
	
	public void next(){
		log.debug("Go to next wizard page");
		
		Button button = new PushButton("Next >");
		checkButtonEnabled(button);
		button.click();
		currentPage++;
	}
	
	public void back(){
		log.debug("Go to previous wizard page");
		Button button = new PushButton("< Back");
		checkButtonEnabled(button);
		button.click();
		currentPage--;
	}
	
	protected void checkButtonEnabled(Button button){
		if (!button.isEnabled()){
			throw new JFaceLayerException("Button '" + button.getText() + "' is not enabled");
		}
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
