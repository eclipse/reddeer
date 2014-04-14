package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import org.eclipse.swt.widgets.Control;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.AbstractShell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.matcher.AndMatcher;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
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
        new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	    new ContextMenu("Delete").select();
		new DefaultShell("Delete Resources");
		new CheckBox().toggle(deleteFromFileSystem);
		DefaultShell shell = new DefaultShell();
		String deleteShellText = shell.getText();
		new PushButton("OK").click();
		try {
			new WaitWhile(new ShellWithTextIsActive(deleteShellText),TimePeriod.LONG);
		} catch(WaitTimeoutExpiredException e) {
			new ShellWithButton(deleteShellText, "Continue");
			new PushButton("Continue");
			new WaitWhile(new ShellWithTextIsActive(deleteShellText),TimePeriod.LONG);
		}
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
	
	/**
	 * Returns shell with a given title and containing a button with specified label.
	 * This is used only when deleting a project.
	 */
	private class ShellWithButton extends AbstractShell {
		
		public ShellWithButton(String title, String buttonLabel) {
			Matcher<String> titleMatcher = new WithTextMatcher(title);
			Matcher<String> buttonMatcher = new ContainsButton(buttonLabel);
			@SuppressWarnings("unchecked")
			Matcher<String> matcher = new AndMatcher(titleMatcher, buttonMatcher);
			try {
				swtShell = ShellLookup.getInstance().getShell(matcher);
				setFocus();
			} catch (Exception e) {
				throw new SWTLayerException("No shell with title '" + title + "' and button '" + buttonLabel + "' is available", e);
			}	
		}
	}
	
	/**
	 * Matcher for control widget containing a button with a specified label.
	 */
	private class ContainsButton extends BaseMatcher<String> {

		private String buttonLabel;
		
		public ContainsButton(String buttonLabel) {
			this.buttonLabel = buttonLabel;
		}
		
		@Override
		public boolean matches(Object obj) {
			if(obj instanceof Control) {
				final Control control = (Control) obj;
				ReferencedComposite ref = new ReferencedComposite() {
					@Override
					public Control getControl() {
						return control;
					}
				};
				try {
					new PushButton(ref, buttonLabel);
					return true;
				} catch (SWTLayerException e) {
					// ok, this control doesn't contain the button
				}
			}
			return false;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("containing button '" + buttonLabel + "'");
		}
		
	}
}
