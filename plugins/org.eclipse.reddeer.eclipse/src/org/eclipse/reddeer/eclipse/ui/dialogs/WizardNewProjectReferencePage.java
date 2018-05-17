/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
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
	public WizardNewProjectReferencePage setProjectReferences(String... referencedProjects) {
		log.debug("Set Project references to: ");
		DefaultTable tbProjectReferences = new DefaultTable(this);
		for (String tableItemLabel : referencedProjects) {
			log.debug(tableItemLabel);
			TableItem tiReferencedProject = tbProjectReferences
					.getItem(tableItemLabel);
			tiReferencedProject.setChecked(true);
		}
		return this;
	}
}