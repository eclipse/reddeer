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
package org.jboss.reddeer.eclipse.core.resources;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

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
		new ContextMenu("Open With", "Other...").select();
		Shell shell = new DefaultShell("Editor Selection");
		new DefaultTreeItem(editor).select();
		new OkButton().click();
		new WaitWhile(new ShellIsAvailable(shell));
	}
}
