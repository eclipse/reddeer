/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.ui.dialogs;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;

/**
 * Second page of New General Project wizard
 * 
 * @author vlado pakan
 * 
 */
public class WizardNewProjectReferencePage extends WizardPage {
	
	private final Logger log = Logger
			.getLogger(WizardNewProjectReferencePage.class);
	
	public WizardNewProjectReferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets a given project references.
	 * 
	 * @param referencedProjects Project references
	 */
	public void setProjectReferences(String... referencedProjects) {
		log.debug("Set Project references to: ");
		DefaultTable tbProjectReferences = new DefaultTable(referencedComposite);
		for (String tableItemLabel : referencedProjects) {
			log.debug(tableItemLabel);
			TableItem tiReferencedProject = tbProjectReferences
					.getItem(tableItemLabel);
			tiReferencedProject.setChecked(true);
		}
	}
}