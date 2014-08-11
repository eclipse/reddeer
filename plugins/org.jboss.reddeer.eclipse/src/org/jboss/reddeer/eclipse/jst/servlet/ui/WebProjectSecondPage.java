package org.jboss.reddeer.eclipse.jst.servlet.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * The second wizard page for creating web project.
 */
public class WebProjectSecondPage extends WizardPage{
	
	public void editSourceFoldersOnBuildPath(String sourceFolder, String newVaule){
		new DefaultTreeItem(sourceFolder).select();
		new PushButton("Edit...").click();
		new DefaultShell("Edit Source Folder");
		new DefaultText().setText(newVaule);
		new OkButton().click();
	}
	
	public void removeSourceFoldersOnBuildPath(String sourceFolder){
		new DefaultTreeItem(sourceFolder).select();
		new PushButton("Remove").click();
	}
	
	public void addSourceFoldersOnBuildPath(String newVaule){
		new PushButton("Add Folder...").click();
		new DefaultShell("Add Source Folder");
		new DefaultText().setText(newVaule);
		new OkButton().click();
	}
	
	public void setDefaultOutputFolder(String folder){
		new LabeledText("Default output folder:").setText(folder);
	}
	
	public List<String> getSourceFolders(){
		List<String> toReturn = new ArrayList<String>();
		for(TreeItem item: new DefaultTree().getAllItems()){
			toReturn.add(item.getText());
		}
		return toReturn;
	}
	
	
	
	

}
