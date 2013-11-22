package org.jboss.reddeer.eclipse.test.mylyn.tasks.ui.view;

import org.jboss.reddeer.eclipse.mylyn.tasks.ui.view.TaskListView;
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
public class TaskListViewTest extends RedDeerTest {

	@Test
	public void getRepoTest() {
		
		TaskListView listView = new TaskListView();	
		listView.open();
		listView.createLocalTaskTest();		
		listView.open();
				
		TreeItem retTask = (DefaultTreeItem)listView.getTask ("Uncategorized", "New Task");
		assertTrue ("task was found", (retTask.getText().equals("New Task")));
	}	
}


