/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.mylyn.tasks.ui.view;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.eclipse.reddeer.eclipse.mylyn.tasks.ui.views.TaskRepositoriesView;
import org.eclipse.reddeer.eclipse.mylyn.tasks.ui.views.TaskRepository;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
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
		assertFalse ("repos are found", repositories.isEmpty());
	}	
}
