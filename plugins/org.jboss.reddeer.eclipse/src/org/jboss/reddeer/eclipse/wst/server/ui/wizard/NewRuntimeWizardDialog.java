package org.jboss.reddeer.eclipse.wst.server.ui.wizard;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.core.condition.JobIsKilled;
import org.jboss.reddeer.jface.wizard.WizardDialog;

/**
 * Represents the wizard for creating new servers. It provides access to the first wizard page {@link NewRuntimeWizardPage}. 
 * Since the other pages depend on the selection of the concrete runtime type this wizard does not provide them.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRuntimeWizardDialog extends WizardDialog{

	public static final String DIALOG_TITLE = "New Server Runtime Environment";

	@Override
	public void finish(TimePeriod timeout) {
		// workaround due to JBDS-3596
		new WaitUntil(new JobIsKilled("Refreshing server adapter list"), TimePeriod.LONG, false);
		super.finish(timeout);
	}

	@Override
	public void cancel() {
		// workaround due to JBDS-3596
		new WaitUntil(new JobIsKilled("Refreshing server adapter list"), TimePeriod.LONG, false);
		super.cancel();
	}
	
}
