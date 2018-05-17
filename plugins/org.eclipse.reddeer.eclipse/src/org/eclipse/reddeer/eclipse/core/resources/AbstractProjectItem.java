/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.core.resources;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * 
 * Abstract project item implementation.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0.0
 */
public abstract class AbstractProjectItem extends DefaultResource implements ProjectItem {

	public AbstractProjectItem(TreeItem treeItem) {
		super(treeItem);
	}

	@Override
	public void openWith(String editor) {
		select();
		new ContextMenuItem("Open With", "Other...").select();
		Shell shell = new DefaultShell("Editor Selection");
		new DefaultTreeItem(editor).select();
		new OkButton().click();
		new WaitWhile(new ShellIsAvailable(shell));
	}
}
