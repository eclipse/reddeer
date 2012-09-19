package org.jboss.reddeer.eclipse.ui.dialogs;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Second page of New General Project wizard
 * 
 * @author vpakan
 * 
 */
public class WizardNewProjectReferencePage extends WizardPage {
	
	private final Logger log = Logger
			.getLogger(WizardNewProjectReferencePage.class);

	public WizardNewProjectReferencePage(
			BasicNewProjectResourceWizard wizardDialog) {
		super(wizardDialog, 2);
	}

	public void setProjectReferences(String... referencedProjects) {
		show();
		log.debug("Set Project references to: ");
		SWTBotTable tbProjectReferences = Bot.get().table();
		for (String tableItemLabel : referencedProjects) {
			log.debug(tableItemLabel);
			SWTBotTableItem tiReferencedProject = tbProjectReferences
					.getTableItem(tableItemLabel);
			tiReferencedProject.check();
		}
	}
}
