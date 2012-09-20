package org.jboss.reddeer.eclipse.ui.wizards.datatransfer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Wizard page for importing external projects into the workspace.
 * 
 * @author Lucia Jelinkova
 *
 */
public class WizardProjectsImportPage extends WizardPage {

	public WizardProjectsImportPage(WizardDialog wizardDialog, int pageIndex) {
		super(wizardDialog, pageIndex);
	}
	
	public static class ImportProject {
		
		public boolean isChecked;
		
		public String name;
		
		@Override
		public String toString() {
			return "ImportProject[" + isChecked + ", " + name + "]";
		}
	}

	public void setRootDirectory(String directory){
		setPath("Select root directory:", directory);
	}
	
	public void setArchiveFile(String file){
		setPath("Select archive file:", file);
	}

	public void copyProjectsIntoWorkspace(boolean copy){
		if (isFileSystem()){
			new CheckBox("Copy projects into workspace").toggle(copy);
		} else {
			throw new EclipseLayerException("You cannot set Copy projects into workspace checkbox when you're importing from ZIP file");
		}
	}
	
	public List<ImportProject> getProjects(){
		List<ImportProject> projects = new ArrayList<ImportProject>();
		
		DefaultTree projectsTree = getProjectsTree();
		for (TreeItem item : projectsTree.getItems()){
			ImportProject project = new ImportProject();
			project.isChecked = item.isChecked();
			project.name = getProjectLabel(item.getText());
			projects.add(project);
		}
		
		return projects;
	}
	
	public void selectProjects(String... projects){
		deselectAllProjects();
		DefaultTree projectsTree = getProjectsTree();
		
		for (String projectName : projects){
			TreeItem  projectItem = getProjectTreeItem(projectsTree, projectName);
			projectItem.check();
		}
	}
	
	public void selectAllProjects(){
		new PushButton("Select All").click();
	}
	
	public void deselectAllProjects(){
		new PushButton("Deselect All").click();
	}
	
	@SuppressWarnings("unchecked")
	public void setPath(String radioText, String path){
		new RadioButton(radioText).click();
		new DefaultText(new EnabledTextMatcher()).setText(path);
		new WaitUntil(new ProjectIsLoaded(getProjectsTree()));
	}
	
	private boolean isFileSystem() {
		return new RadioButton("Select root directory:").isSelected();
	}
	
	private DefaultTree getProjectsTree() {
		return new DefaultTree();
	}
	
	private TreeItem getProjectTreeItem(DefaultTree projectsTree, String projectName) {
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

		private DefaultTree tree;
		
		private ProjectIsLoaded(DefaultTree tree) {
			this.tree = tree;
		}
		
		@Override
		public boolean test() {
			return !tree.getItems().isEmpty();
		}

		@Override
		public String description() {
			return "At least one project is loaded";
		}
	}
	
	private class EnabledTextMatcher extends TypeSafeMatcher<Widget> {

		@Override
		public void describeTo(Description description) {
		}

		@Override
		public boolean matchesSafely(Widget w) {
			return w instanceof Text && ((Text) w).isEnabled();
		}
	}
}
