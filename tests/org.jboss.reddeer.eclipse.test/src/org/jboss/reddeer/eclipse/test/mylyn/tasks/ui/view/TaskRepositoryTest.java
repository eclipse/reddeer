package org.jboss.reddeer.eclipse.test.mylyn.tasks.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.mylyn.tasks.ui.view.TaskRepositoriesView;
import org.jboss.reddeer.eclipse.mylyn.tasks.ui.view.TaskRepository;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 
 * @author ldimaggi
 * 
 */
public class TaskRepositoryTest extends RedDeerTest {

	@Test
	public void getRepoTest() {

		TaskRepositoriesView view = new TaskRepositoriesView();

		new ShellMenu("Window", "Show View", "Other...").select();
		DefaultTreeItem taskRepositories = new DefaultTreeItem("Mylyn",
				"Task Repositories");
		taskRepositories.select();
		new PushButton("OK").click();

		AbstractWait.sleep(TimePeriod.NORMAL.getSeconds());
		DefaultTree RepoTree = new DefaultTree();
		List<TreeItem> repoItems = RepoTree.getAllItems();

		assertFalse("repos are found", repoItems.isEmpty());
		
		ArrayList<String> repoList = new ArrayList<String>();
		int i = 0;
		for (TreeItem item : repoItems) {
			repoList.add(i++, item.getText());
		}
		int elementIndex = repoList.indexOf("Red Hat Bugzilla");

		TaskRepository testRepo = new TaskRepository(repoItems.get(elementIndex));
		assertTrue("repo name " + testRepo.getName() + " is empty", testRepo.getName().equals("Red Hat Bugzilla"));
		
		try {
			testRepo.open();
		}
		catch (UnsupportedOperationException U) {
			
		}
		
	}
}
