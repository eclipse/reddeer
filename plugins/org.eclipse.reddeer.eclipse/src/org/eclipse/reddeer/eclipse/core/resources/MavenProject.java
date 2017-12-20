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
package org.eclipse.reddeer.eclipse.core.resources;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.eclipse.m2e.core.ui.internal.dialogs.UpdateMavenProjectsDialog;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.menu.ContextMenu;

/**
 * Represents a project with maven nature inside of explorer view.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0
 */
public class MavenProject extends DefaultProject {

	public MavenProject(TreeItem item) {
		super(item);
	}

	@Override
	public String[] getNatureIds() {
		return new String[] { "org.eclipse.m2e.core.maven2Nature" };
	}
	
	public void updateMavenProject() {
		updateMavenProject(TimePeriod.LONG);
	}
	
	public void updateMavenProject(TimePeriod waitForJobs) {
		this.select();
		new ContextMenu().getItem("Maven", "Update Project...").select();
		UpdateMavenProjectsDialog updateProject = new UpdateMavenProjectsDialog();
		updateProject.clean(true);
		updateProject.ok(waitForJobs);
	}
}