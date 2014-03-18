package org.jboss.reddeer.eclipse.test.ui.ide;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.ui.ide.RepoConnectionDialog;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
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
	RepoConnectionDialog repoConnectionDialog = null;
	@Test
	public void getDialogTest() {
		
		new ShellMenu("Window", "Show View", "Other...").select();		
		DefaultTreeItem taskRepositories = new DefaultTreeItem ("Mylyn", "Task Repositories");
		taskRepositories.select();		
		new PushButton("OK").click();
				
		AbstractWait.sleep(TimePeriod.NORMAL);
		
		DefaultTree RepoTree = new DefaultTree();
		List<TreeItem> repoItems = RepoTree.getAllItems();
		
		ArrayList<String> repoList = new ArrayList<String>();
		int i = 0;
		for (TreeItem item : repoItems) {
			repoList.add(i++, item.getText());
		}
		int elementIndex = repoList.indexOf("Eclipse.org");
		
		repoItems.get(elementIndex).select();	
		new ShellMenu("File", "Properties").select();  
		
		repoConnectionDialog = new RepoConnectionDialog();
		
		assertTrue ("Properties title matches", repoConnectionDialog.getText().equals("Properties for Task Repository"));
		
		repoConnectionDialog.validateSettings();

		assertTrue("Repo Connection Properties Invalid", new LabeledText("Bugzilla Repository Settings").getText().contains("Repository is valid"));
	
		repoConnectionDialog.close();
		
		repoConnectionDialog = null;
		
	}
	
	@Override
	public void tearDown(){
		if (repoConnectionDialog != null){
			repoConnectionDialog.close();
		}
	}
}
