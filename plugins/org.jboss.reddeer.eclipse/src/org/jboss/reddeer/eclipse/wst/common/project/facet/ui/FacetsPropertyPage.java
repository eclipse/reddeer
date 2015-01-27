package org.jboss.reddeer.eclipse.wst.common.project.facet.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.ui.dialogs.ProjectPropertyPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * "Project Facets" property page. 
 * @author Pavol Srna
 * 
 */
public class FacetsPropertyPage extends ProjectPropertyPage {
	
	public static final String NAME = "Project Facets"; 
	
	/**
	 * Constructs the property page with a given project and {@value #NAME}.
	 * 
	 * @param project Project name
	 */
	public FacetsPropertyPage(Project project) {
		super(project, NAME);
	}
	
	/**
	 * Selects the given facet. 
	 * 
	 * @param facetName
	 */
	public void selectFacet(String facetName){
		new DefaultTreeItem(new DefaultTree(1), facetName).setChecked(true);
	}
	
	/**
	 * Returns selected facet version.
	 * @param facetName
	 * @return version
	 */
	public String getSelectedVersion(String facetName){
		return new DefaultTreeItem(new DefaultTree(1), facetName).getCell(1);
	}
	
	/**
	 * Selects facet version.
	 * @param facetName
	 * @param version
	 */
	public void selectVersion(String facetName, String version){
		TreeItem facet = new DefaultTreeItem(new DefaultTree(1), facetName);
		facet.select();
		new ContextMenu("Change Version...").select();
		new LabeledCombo("Version:").setSelection(version);
		new OkButton().click();
	}
	
	/**
	 * Returns names of all selected facets
	 * @return
	 */
	public List<TreeItem> getSelectedFacets(){
		List<TreeItem> facets = new ArrayList<TreeItem>();
		for(TreeItem i : new DefaultTree(1).getItems()){
			if(i.isChecked())
				facets.add(i);
		}
		return facets;
	}
	
	
}
