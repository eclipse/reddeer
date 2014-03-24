package org.jboss.reddeer.eclipse.test.ui.ide;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.ui.ide.BuildServerDialog;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
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
public class BuildServerDialogTest extends RedDeerTest {

	/* Cannot use machydra Jenkins server due to - https://issues.jboss.org/browse/JBDS-2965 */
	protected final String SERVERURL = "https://hudson.jboss.org/hudson/";
	
	BuildServerDialog buildServerDialog = null;
	@Test
	public void getDialogTest() {
		
		new ShellMenu("Window", "Show View", "Other...").select();		
		//DefaultTreeItem taskRepositories = 
		new DefaultTreeItem ("Mylyn", "Builds").select();
		new PushButton("OK").click();
				
		AbstractWait.sleep(TimePeriod.NORMAL.getSeconds());

		new DefaultToolItem("New Build Server Location").click();
		
		new DefaultShell ("New Repository");
		new DefaultTreeItem ("Hudson (supports Jenkins)").select();
		new PushButton("Next >").click();
		
		new DefaultShell ("New Build Server");
		new LabeledText ("Server:").setText(SERVERURL);
		new LabeledText ("Label:").setText(SERVERURL);
		new PushButton("Finish").click();
				
		DefaultTree buildServerTree = new DefaultTree();
		List<TreeItem> buildServerItems = buildServerTree.getAllItems();
		
		ArrayList<String> repoList = new ArrayList<String>();
		int i = 0;
		for (TreeItem item : buildServerItems) {
			repoList.add(i++, item.getText());
		}
		int elementIndex = repoList.indexOf(SERVERURL);
		
		buildServerItems.get(elementIndex).select();	
		new ShellMenu("File", "Properties").select();  
		
		buildServerDialog = new BuildServerDialog();
		
		assertTrue ("Properties title matches", buildServerDialog.getText().equals("Build Server Properties"));
		
		buildServerDialog.validateSettings();

		assertTrue("Build Server Properties Invalid", new LabeledText("Build Server Properties").getText().contains("Repository is valid"));
	
		buildServerDialog.close();
		
		buildServerDialog = null;
		
	}
	
	@Override
	public void tearDown(){
		if (buildServerDialog != null){
			buildServerDialog.close();
		}
	}
}
