package org.jboss.reddeer.eclipse.mylyn.tasks.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.workbench.api.View;
import org.jboss.reddeer.workbench.impl.view.AbstractView;

/**
 * Represents the Build List view - to support Mylyn automated tests. 
 *  
 * @author ldimaggi
 *
 */
public class MylynBuildView extends AbstractView implements View{
	
	public static final String TITLE = "Builds";
	
	public MylynBuildView() {
		super(TITLE);
	}

	public List<MylynBuild> getMylynBuilds(){
		List<MylynBuild> theMylynBuilds = new ArrayList<MylynBuild>();

		Tree tree;
		try {
			tree = new DefaultTree();
		} catch (SWTLayerException e){
			return new ArrayList<MylynBuild>();
		}
		for (TreeItem item : tree.getItems()){
			theMylynBuilds.add(new MylynBuild(item));
		}
		return theMylynBuilds;
	}

	public MylynBuild getMylynBuild(String name){
		for (MylynBuild repository : getMylynBuilds()){
			if (repository.getName().equals(name)){
				return repository;
			}
		}
		throw new EclipseLayerException("There is no build with name " + name);
	}

	protected Tree getBuildsTree(){
		open();
		return new DefaultTree();
	}
	
	/* Method to locate and select a build in the build list view  */
	public TreeItem getBuild (String buildName) {
		new DefaultTree();
		DefaultTreeItem theBuild = new DefaultTreeItem (buildName);
		theBuild.select();
		return theBuild;
	}
	
	/* For use in the Build List View */
	public void createBuildServer (String serverURL) {
		log.info("Creating New Build Server - " + serverURL);
		new DefaultToolItem("New Build Server Location").click();
		
		new DefaultShell ("New Repository");
		new DefaultTreeItem ("Hudson (supports Jenkins)").select();
		new PushButton("Next >").click();
		
		new DefaultShell ("New Build Server");
		new LabeledText ("Server:").setText(serverURL);
		new LabeledText ("Label:").setText(serverURL);
		new PushButton("Finish").click();
	}
	
}
