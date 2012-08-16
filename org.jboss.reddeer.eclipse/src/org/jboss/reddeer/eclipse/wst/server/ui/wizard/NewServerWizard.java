package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Represents the wizard for creating new servers. It provided access to the first wizard page {@link NewServerWizardPage}. 
 * Since the other pages depend on the selection of the concrete server type this wizard does not provide them.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewServerWizard extends WizardDialog {

	public static final String TITLE = "New Server";
	
	public NewServerWizard() {
	}

	public NewServerWizardPage getFirstPage(){
		return new NewServerWizardPage(this);
	}
	
	@Override
	public void finish() {
		SWTBotShell active = Bot.get().activeShell();
		super.finish();
		Bot.get().waitUntil(shellCloses(active));
	}
	
	@Override
	public void cancel() {
		SWTBotShell active = Bot.get().activeShell();
		super.cancel();
		Bot.get().waitUntil(shellCloses(active));
	}
}
