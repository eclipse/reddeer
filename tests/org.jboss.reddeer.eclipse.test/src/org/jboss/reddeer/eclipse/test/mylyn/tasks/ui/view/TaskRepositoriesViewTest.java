package org.jboss.reddeer.eclipse.test.mylyn.tasks.ui.view;

import java.util.List;

import org.jboss.reddeer.eclipse.mylyn.tasks.ui.view.TaskRepositoriesView;
import org.jboss.reddeer.eclipse.mylyn.tasks.ui.view.TaskRepository;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * 
 * @author ldimaggi
 *
 */
public class TaskRepositoriesViewTest extends RedDeerTest {

	@Test
	public void getRepoTest() {
		
		TaskRepositoriesView view = new TaskRepositoriesView();	
		view.open();
	
		List<TaskRepository> repositories = view.getTaskRepositories();
		AbstractWait.sleep(TimePeriod.NORMAL.getSeconds());
		assertFalse ("repos are found", repositories.isEmpty());
	}	
}
