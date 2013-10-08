package org.jboss.reddeer.eclipse.test.ui.ide;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.mylyn.tasks.ui.wizards.NewRepositoryWizard;
import org.jboss.reddeer.eclipse.ui.ide.RepoConnectionDialog;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 
 * @author ldimaggi
 * 
 */
public class RepoConnectionDialogTest extends RedDeerTest {

	@Test
	public void getDialogTest() {
		
		new ShellMenu("Window", "Show View", "Other...").select();		
		DefaultTreeItem taskRepositories = new DefaultTreeItem ("Mylyn", "Task Repositories");
		taskRepositories.select();		
		new PushButton("OK").click();
				
		AbstractWait.sleep(TimePeriod.NORMAL.getSeconds());
		
		DefaultTree RepoTree = new DefaultTree();
		List<TreeItem> repoItems = RepoTree.getAllItems();
		
		ArrayList<String> repoList = new ArrayList<String>();
		int i = 0;
		for (TreeItem item : repoItems) {
			repoList.add(i++, item.getText());
		}
		int elementIndex = repoList.indexOf("Red Hat Bugzilla");
		
		repoItems.get(elementIndex).select();	
		new ShellMenu("File", "Properties").select();  
		
		RepoConnectionDialog theRepoDialog = new RepoConnectionDialog();
		assertTrue ("Properties title matches", theRepoDialog.getText().equals("Properties for Task Repository"));
		
		theRepoDialog.validateSettings();
		assertTrue("Repo Connection Properties Invalid", new LabeledText("Bugzilla Repository Settings").getText().contains("Repository is valid"));
	
		theRepoDialog.close();
		
	}
}
