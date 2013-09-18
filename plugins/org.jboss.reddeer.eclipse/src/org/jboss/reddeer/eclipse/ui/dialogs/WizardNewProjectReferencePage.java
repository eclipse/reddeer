package org.jboss.reddeer.eclipse.ui.dialogs;

import org.apache.log4j.Logger;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.impl.table.DefaultTable;

/**
 * Second page of New General Project wizard
 * 
 * @author vlado pakan
 * 
 */
public class WizardNewProjectReferencePage extends WizardPage {
	
	private final Logger log = Logger
			.getLogger(WizardNewProjectReferencePage.class);

	public void setProjectReferences(String... referencedProjects) {
		log.debug("Set Project references to: ");
		DefaultTable tbProjectReferences = new DefaultTable();
		for (String tableItemLabel : referencedProjects) {
			log.debug(tableItemLabel);
			TableItem tiReferencedProject = tbProjectReferences
					.getItem(tableItemLabel);
			tiReferencedProject.setChecked(true);
		}
	}
}
