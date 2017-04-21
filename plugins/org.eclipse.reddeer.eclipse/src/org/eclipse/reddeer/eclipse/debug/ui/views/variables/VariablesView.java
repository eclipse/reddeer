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
package org.eclipse.reddeer.eclipse.debug.ui.views.variables;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Variables view
 * 
 * @author Andrej Podhradsky
 *
 */
public class VariablesView extends WorkbenchView {

	/**
	 * Instantiates a new variables view.
	 */
	public VariablesView() {
		super("Variables");
	}

	/**
	 * Gets a value of a specified variable.
	 * 
	 * @param variablePath
	 *            path of the variable
	 * @return value of the variable
	 */
	public String getValue(final String... variablePath) {
		open();
		new WaitUntil(new AbstractWaitCondition() {

			@Override
			public boolean test() {
				try {
					TreeItem variable = new DefaultTreeItem(new DefaultTree(cTabItem), variablePath);
					variable.select();
					return variable.isSelected();
				} catch (Exception e) {
					return false;
				}
			}

			@Override
			public String description() {
				return "Variable is not selected";
			}
		});
		new WaitUntil(new AbstractWaitCondition() {

			@Override
			public boolean test() {
				return new DefaultStyledText(cTabItem).getText().length() > 0;
			}

			@Override
			public String description() {
				return "Variable is empty";
			}
		});

		return new DefaultStyledText(cTabItem).getText();
	}

}
