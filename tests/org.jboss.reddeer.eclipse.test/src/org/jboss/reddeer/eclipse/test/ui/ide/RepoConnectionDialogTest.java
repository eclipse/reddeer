package org.jboss.reddeer.eclipse.test.ui.ide;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.ui.ide.RepoConnectionDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author ldimaggi
 * 
 */
@RunWith(RedDeerSuite.class)
public class RepoConnectionDialogTest  {
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
		// On Linux Secure Storage shell is opened
		try{
			new DefaultShell("Secure Storage").close();
		} catch (SWTLayerException swtle){
			// do nothing shell was not opened
		}
		
		repoConnectionDialog = new RepoConnectionDialog();
		
		assertTrue ("Properties title matches", repoConnectionDialog.getText().equals("Properties for Task Repository"));
		
		repoConnectionDialog.validateSettings();

		assertTrue("Repo Connection Properties Invalid", new LabeledText("Bugzilla Repository Settings").getText().contains("Repository is valid"));
	
		repoConnectionDialog.close();
		
		repoConnectionDialog = null;
		
	}
	
	@After
	public void tearDown(){
		if (repoConnectionDialog != null){
			repoConnectionDialog.close();
		}
	}
}
