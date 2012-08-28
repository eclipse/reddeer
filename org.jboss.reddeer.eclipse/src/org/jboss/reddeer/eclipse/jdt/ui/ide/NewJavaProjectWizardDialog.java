package org.jboss.reddeer.eclipse.jdt.ui.ide;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.swt.condition.AllRunningJobsAreNotActive;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.WaitUntilCondition;
import org.jboss.reddeer.swt.wait.WaitWhileCondition;

public class NewJavaProjectWizardDialog extends NewWizardDialog{
	
	public NewJavaProjectWizardDialog() {
		super("Java", "Java Project");
	}
	
	@Override
	public void finish(){
		finish(false);
	}
	
	public void finish(boolean openAssociatedPerspective) {
		log.debug("Finish wizard dialog");
		new PushButton("Finish").click();
		new WaitUntilCondition(new ShellWithTextIsActive("Open Associated Perspective?"));
		DefaultShell shell = new DefaultShell();
		if (new DefaultShell().getText().equals("Open Associated Perspective?")){
			if (openAssociatedPerspective){
				new PushButton("Yes").click();
			}else{
				new PushButton("No").click();
			}
		}else{
			log.info("Shell 'Open Associated Perspective' wasn't shown");
		}
		new WaitWhileCondition(new ShellWithTextIsActive(shell.getText()), 10000);
		new WaitUntilCondition(new AllRunningJobsAreNotActive(), 30000);
	}

}
