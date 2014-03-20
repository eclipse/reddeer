package org.jboss.reddeer.eclipse.test.mylyn.tasks.ui.view;

import org.jboss.reddeer.eclipse.mylyn.tasks.ui.view.MylynBuildView;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 
 * @author ldimaggi
 *
 */
public class MylynBuildViewTest extends RedDeerTest {
	/* Cannot use machydra Jenkins server due to - https://issues.jboss.org/browse/JBDS-2965 */
	protected final String SERVERURL = "https://hudson.jboss.org/hudson/";

	@Test
	public void getBuildTest() {
		
		MylynBuildView listView = new MylynBuildView();	
		listView.open();
		listView.createBuildServer(SERVERURL);	
		listView.open();
				
		TreeItem retBuild = (DefaultTreeItem)listView.getBuild (SERVERURL);
		assertTrue ("Build was found", (retBuild.getText().equals(SERVERURL)));
	}	
}


