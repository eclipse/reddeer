package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.jboss.reddeer.swt.condition.JobsAreNotActive;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.AbstractTreeItem;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.util.Jobs;
import org.jboss.reddeer.swt.wait.WaitUntilCondition;
import org.jboss.reddeer.swt.wait.WaitWhileCondition;

/**
 * Represents a project on {@link PackageExplorer}.
 * 
 * @author Vlado Pakan
 * 
 */
public class Project {
	protected final Logger log = Logger.getLogger(Project.class);
	
	private AbstractTreeItem treeItem;
	
	private String name;

	public Project(AbstractTreeItem treeItem) {
		this.treeItem = treeItem;
		name = parseName(this.treeItem.getText());
	}

	public void delete(boolean deleteFromFileSystem) {
		select();
        log.debug("Delete project " + name + " via Package Explorer");
	    new ContextMenu("Delete").select();
		new DefaultShell("Delete Resources");
		SWTBotCheckBox chbDeleteFromFileSystem = Bot.get().checkBox();
		if ((chbDeleteFromFileSystem.isChecked() && !deleteFromFileSystem) ||
		    (!chbDeleteFromFileSystem.isChecked() && deleteFromFileSystem)){
		    chbDeleteFromFileSystem.click();
		}
		DefaultShell shell = new DefaultShell();
		new PushButton("OK").click();
		new WaitWhileCondition(new ShellWithTextIsActive(shell.getText()), 10000);
		new WaitUntilCondition(new JobsAreNotActive(Jobs.BUILDING_WORKSPACE_JOB,
          Jobs.COMPACTING_RESOURCE_MODEL,
		  Jobs.LOADING_JOB),
		  30000);
	}

	public void select() {
		treeItem.select();
	}

	public String parseName(String label){
		if (!label.contains("[")){
			return label.trim();
		}
		return treeItem.getText().substring(0, treeItem.getText().indexOf("[")).trim();
	}
	
	public String getName() {
		return name;
	}
	
	public AbstractTreeItem getTreeItem (){
		return treeItem;
	}

	public boolean containsItem(String... path) {
		boolean result = false;
		try {
			getProjectItem(path);
			result = true;
		} catch (WidgetNotFoundException wnf) {
			result = false;
		}
		return result;
	}
	
	public ProjectItem getProjectItem(String... path){
		AbstractTreeItem item = treeItem;
		int index = 0;
		while (index < path.length){
			item = item.getChild(path[index]);
			index++;
		}
		return new ProjectItem(item, this, path);
	}
	
	public boolean isSelected(){
		return treeItem.isSelected();
	}
}
