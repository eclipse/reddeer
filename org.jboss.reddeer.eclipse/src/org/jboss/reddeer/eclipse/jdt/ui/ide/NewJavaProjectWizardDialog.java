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
		if (new DefaultShell().getText().equals("Open Associated Perspective")){
			if (openAssociatedPerspective){
				new PushButton("Yes").click();
			}else{
				new PushButton("No").click();
			}
		}
		new WaitUntilCondition(new AllRunningJobsAreNotActive());
	}

}
