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
package org.jboss.reddeer.eclipse.m2e.core.ui.wizard;

import static org.jboss.reddeer.common.wait.WaitProvider.waitUntil;
import static org.jboss.reddeer.common.wait.WaitProvider.waitWhile;

import org.jboss.reddeer.common.wait.GroupWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.TableHasRows;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Second wizard page for creating maven project
 * @author rawagner
 *
 */
public class MavenProjectWizardArchetypePage extends WizardPage{
	
	/**
	 * Select archetype.
	 *
	 * @param groupId if null then archetype group id is ignored
	 * @param artifactId if null then archetype artifact id is ignored
	 * @param version if null then archetype version is ignored
	 */
	public void selectArchetype(String groupId, String artifactId, String version){
		Table t = new DefaultTable();
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
		new DefaultCombo(0).setSelection(catalog);
		new GroupWait(TimePeriod.VERY_LONG, waitWhile(new JobIsRunning()),
				waitUntil(new TableHasRows(new DefaultTable())));
	}
	
	/**
	 * Get current catalog.
	 *
	 * @return catalog name
	 */
	public String getArchetypeCatalog(){
		return new DefaultCombo(0).getText();
	}

}
