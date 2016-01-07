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
package org.jboss.reddeer.eclipse.debug.core;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

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
					TreeItem variable = new DefaultTreeItem(variablePath);
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
				return new DefaultStyledText().getText().length() > 0;
			}

			@Override
			public String description() {
				return "Variable is empty";
			}
		});

		return new DefaultStyledText().getText();
	}

}
