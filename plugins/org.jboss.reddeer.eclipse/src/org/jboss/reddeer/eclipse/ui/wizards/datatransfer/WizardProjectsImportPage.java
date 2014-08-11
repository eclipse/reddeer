package org.jboss.reddeer.eclipse.ui.wizards.datatransfer;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.jface.wizard.WizardDialog;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Wizard page for importing external projects into the workspace.
 * 
 * @author Lucia Jelinkova
 *
 */
public class WizardProjectsImportPage extends WizardPage {
	
	private static final Logger log = Logger.getLogger(WizardProjectsImportPage.class);

	/**
	 * A wizard page should not know on which page index it is displayed. The
	 * wizard page can also exist outside WizardDialog. Use no-argument
	 * constructor instead.
	 * 
	 * @param wizardDialog
	 * @param pageIndex
	 */
	@Deprecated
	public WizardProjectsImportPage(WizardDialog wizardDialog, int pageIndex) {
		super(wizardDialog, pageIndex);
	}
	
	public WizardProjectsImportPage() {
		super();
	}
	
	/**
	 * Represents an imported project.
	 */
	public static class ImportProject {
		
		public boolean isChecked;
		
		public String name;
		
		@Override
		public String toString() {
			return "ImportProject[" + isChecked + ", " + name + "]";
		}
	}

	/**
	 * Sets root directory.
	 * 
	 * @param directory Root directory
	 */
	public void setRootDirectory(String directory){
		log.info("Setting root directory to " + directory);
		setPath("Select root directory:", directory);
	}
	
	/**
	 * Sets archive file.
	 * 
	 * @param file File
	 */
	public void setArchiveFile(String file){
		log.info("Settig archive file to " + file);
		setPath("Select archive file:", file);
	}

	/**
	 * Sets whether to copy projects into workspace.
	 * 
	 * @param copy Indicates whether to copy projects into workspace
	 */
	public void copyProjectsIntoWorkspace(boolean copy){
		log.info("Setting copy checkbox to " + copy);
		if (isFileSystem()){
			new CheckBox("Copy projects into workspace").toggle(copy);
		} else {
			throw new EclipseLayerException("You cannot set Copy projects into workspace checkbox when you're importing from ZIP file");
		}
	}
	
	/**
	 * Returns list of projects.
	 * 
	 * @return List of projects
	 */
	public List<ImportProject> getProjects(){
		List<ImportProject> projects = new ArrayList<ImportProject>();
		
		Tree projectsTree = getProjectsTree();
		for (TreeItem item : projectsTree.getItems()){
			ImportProject project = new ImportProject();
			project.isChecked = item.isChecked();
			project.name = getProjectLabel(item.getText());
			projects.add(project);
		}
		
		return projects;
	}
	
	/**
	 * Selects a given projects.
	 * 
	 * @param projects Projects
	 */
	public void selectProjects(String... projects){
		log.info("Selecting projects");
		deselectAllProjects();
		Tree projectsTree = getProjectsTree();
		
		for (String projectName : projects){
			TreeItem  projectItem = getProjectTreeItem(projectsTree, projectName);
			projectItem.setChecked(true);
		}
	}
	
	/**
	 * Selects all projects.
	 */
	public void selectAllProjects(){
		log.info("Selecting all projects");
		new PushButton("Select All").click();
	}
	
	/**
	 * Deselects all projects.
	 */
	public void deselectAllProjects(){
		log.info("Deselecting all projects");
		new PushButton("Deselect All").click();
	}
	
	protected void setPath(String radioText, String path){
		new RadioButton(radioText).click();
		if(radioText.equals("Select root directory:")){
			new DefaultCombo(0).setText(path);
		} else {
			new DefaultCombo(1).setText(path);
		}
		new PushButton("Refresh").click();
		new WaitUntil(new ProjectIsLoaded(getProjectsTree()), TimePeriod.NORMAL);
	}
	
	private boolean isFileSystem() {
		return new RadioButton("Select root directory:").isSelected();
	}
	
	private Tree getProjectsTree() {
		return new DefaultTree();
	}
	
	private TreeItem getProjectTreeItem(Tree projectsTree, String projectName) {
		for (TreeItem item : projectsTree.getItems()){
			if (projectName.equals(getProjectLabel(item.getText()))){
				return item;
			}
		}
		throw new IllegalStateException("Project " + projectName + " is not available");
	}
	
	private String getProjectLabel(String project){
		return project.substring(0, project.indexOf('(')).trim();
	}
	
	private class ProjectIsLoaded implements WaitCondition {

		private Tree tree;
		
		private ProjectIsLoaded(Tree tree) {
			this.tree = tree;
		}
		
		@Override
		public boolean test() {
			return !tree.getItems().isEmpty();
		}

		@Override
		public String description() {
			return "at least one project is loaded";
		}
	}
}
