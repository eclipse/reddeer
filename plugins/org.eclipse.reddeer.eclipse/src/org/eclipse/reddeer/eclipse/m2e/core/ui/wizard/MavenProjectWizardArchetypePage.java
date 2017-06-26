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
package org.eclipse.reddeer.eclipse.m2e.core.ui.wizard;

import static org.eclipse.reddeer.common.wait.WaitProvider.waitUntil;
import static org.eclipse.reddeer.common.wait.WaitProvider.waitWhile;

import org.eclipse.reddeer.common.wait.GroupWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.TableHasRows;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Second wizard page for creating maven project
 * @author rawagner
 *
 */
public class MavenProjectWizardArchetypePage extends WizardPage{
	
	public MavenProjectWizardArchetypePage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Select archetype.
	 *
	 * @param groupId if null then archetype group id is ignored
	 * @param artifactId if null then archetype artifact id is ignored
	 * @param version if null then archetype version is ignored
	 */
	public void selectArchetype(String groupId, String artifactId, String version){
		Table t = new DefaultTable(referencedComposite);
		int groupColumn = t.getHeaderIndex("Group Id");
		int artColumn = t.getHeaderIndex("Artifact Id");
		int verColumn =  t.getHeaderIndex("Version");
		
		boolean artifactFound = false;
		for(TableItem ti: t.getItems()){
			if((groupId == null || ti.getText(groupColumn).equals(groupId)) 
					&& (artifactId == null || ti.getText(artColumn).equals(artifactId))
					&& (version == null || ti.getText(verColumn).equals(version))){
				ti.select();
				artifactFound = true;
				break;
			}
		}
		if(!artifactFound){
			throw new EclipseLayerException("Unable to find archetype with GAV: "+groupId +","+artifactId+
					","+version);
		}
		
	}
	
	/**
	 * Select catalog.
	 *
	 * @param catalog to choose archetype from
	 */
	public void selectArchetypeCatalog(String catalog){
		new DefaultCombo(referencedComposite, 0).setSelection(catalog);
		new GroupWait(TimePeriod.VERY_LONG, waitWhile(new JobIsRunning()),
				waitUntil(new TableHasRows(new DefaultTable(referencedComposite))));
	}
	
	/**
	 * Get current catalog.
	 *
	 * @return catalog name
	 */
	public String getArchetypeCatalog(){
		return new DefaultCombo(referencedComposite, 0).getText();
	}

}
