package org.jboss.reddeer.eclipse.m2e.core.ui.wizard;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.TableHasRows;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.table.DefaultTable;

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
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
		new WaitUntil(new TableHasRows(new DefaultTable()),TimePeriod.LONG);
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
