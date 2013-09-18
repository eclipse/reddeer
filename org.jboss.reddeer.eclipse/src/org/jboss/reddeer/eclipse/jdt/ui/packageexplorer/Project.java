package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Represents a project on {@link PackageExplorer}.
 * 
 * @author Vlado Pakan
 * 
 */
public class Project {
	protected final Logger log = Logger.getLogger(Project.class);
	
	private TreeItem treeItem;
	
	private String name;
	/**
	 * Creates project represented by treeItem
	 * @param treeItem
	 */
	public Project(TreeItem treeItem) {
		this.treeItem = treeItem;
		name = parseName(this.treeItem.getText());
	}
	/**
	 * Deletes project
	 * @param deleteFromFileSystem
	 */
	public void delete(boolean deleteFromFileSystem) {
		select();
        log.debug("Delete project " + name + " via Package Explorer");
        new ContextMenu("Refresh").select();
        new WaitWhile(new JobIsRunning());
	    new ContextMenu("Delete").select();
		new DefaultShell("Delete Resources");
		SWTBotCheckBox chbDeleteFromFileSystem = Bot.get().checkBox();
		if ((chbDeleteFromFileSystem.isChecked() && !deleteFromFileSystem) ||
		    (!chbDeleteFromFileSystem.isChecked() && deleteFromFileSystem)){
		    chbDeleteFromFileSystem.click();
		}
		DefaultShell shell = new DefaultShell();
		String deleteShellText = shell.getText();
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive(deleteShellText),TimePeriod.LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}
	/**
	 * Selects project
	 */
	public void select() {
		treeItem.select();
	}
	/**
	 * Parses project name and returns project name striped from additional info
	 * displayed in explorer
	 * @param label
	 * @return
	 */
	protected String parseName(String label){
		if (!label.contains("[")){
			return label.trim();
		}
		return treeItem.getText().substring(0, treeItem.getText().indexOf("[")).trim();
	}
	/**
	 * Returns project name 
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns Tree Item representing project
	 * @return 
	 */
	public TreeItem getTreeItem (){
		return treeItem;
	}
	/**
	 * Returns true when project contains item specified by path
	 * @param path
	 * @return
	 */
	public boolean containsItem(String... path) {
		boolean result = false;
		try {
			getProjectItem(path);
			result = true;
		} catch (SWTLayerException swtle) {
			result = false;
		}
		return result;
	}
	/**
	 * Returns Project Item specified by path 
	 * @param path
	 * @return
	 */
	public ProjectItem getProjectItem(String... path){
		TreeItem item = treeItem;
		int index = 0;
		while (index < path.length){
			item = item.getItem(path[index]);
			index++;
		}
		return new ProjectItem(item, this, path);
	}
	/**
	 * Returns true when project is selected 
	 * @return
	 */
	public boolean isSelected(){
		return treeItem.isSelected();
	}
	/**
	 * Returns text of Project displayed in explorer
	 * @return
	 */
	public String getText (){
		return treeItem.getText();
	}
}
