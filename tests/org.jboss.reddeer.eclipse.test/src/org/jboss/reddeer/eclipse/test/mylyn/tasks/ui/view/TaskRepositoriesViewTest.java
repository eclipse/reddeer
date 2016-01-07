/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.test.mylyn.tasks.ui.view;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.jboss.reddeer.eclipse.mylyn.tasks.ui.view.TaskRepositoriesView;
import org.jboss.reddeer.eclipse.mylyn.tasks.ui.view.TaskRepository;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author ldimaggi
 *
 */
@RunWith(RedDeerSuite.class)
public class TaskRepositoriesViewTest {

	@Test
	public void getRepoTest() {
		
		TaskRepositoriesView view = new TaskRepositoriesView();	
		view.open();
	
		List<TaskRepository> repositories = view.getTaskRepositories();
		AbstractWait.sleep(TimePeriod.NORMAL);
		assertFalse ("repos are found", repositories.isEmpty());
	}	
}
