/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.jface.wizard.WizardPage;
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

	/**
	 * Sets a given project references.
	 * 
	 * @param referencedProjects Project references
	 */
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