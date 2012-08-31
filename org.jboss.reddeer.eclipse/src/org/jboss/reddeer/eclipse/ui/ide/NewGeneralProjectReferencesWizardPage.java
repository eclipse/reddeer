package org.jboss.reddeer.eclipse.ui.ide;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Second page of New General Project wizard
 * 
 * @author vpakan
 * 
 */
public class NewGeneralProjectReferencesWizardPage extends WizardPage {
	
	private final Logger log = Logger
			.getLogger(NewGeneralProjectReferencesWizardPage.class);

	public NewGeneralProjectReferencesWizardPage(
			NewGeneralProjectWizardDialog wizardDialog) {
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
