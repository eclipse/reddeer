/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.ui.dialogs;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.jface.condition.WindowIsAvailable;
import org.eclipse.reddeer.jface.window.AbstractWindow;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.hamcrest.Matcher;

/**
 * Represends {@link org.eclipse.ui.dialogs.FilteredItemsSelectionDialog}.
 * 
 * @author lvalach
 *
 */
public class FilteredItemsSelectionDialog extends AbstractWindow {

	/**
	 * Implementations are responsible for making sure given shell is
	 * FilteredItemsSelectionDialog.
	 * 
	 * @param shell
	 *            instance of FilteredItemsSelectionDialog
	 */
	public FilteredItemsSelectionDialog(Shell shell) {
		super(shell);
	}

	/**
	 * Finds FilteredItemsSelectionDialog matching given matchers.
	 * 
	 * @param matchers
	 *            to match FilteredItemsSelectionDialog
	 */
	public FilteredItemsSelectionDialog(Matcher<?>... matchers) {
		super(matchers);
	}

	/**
	 * Shell won't be assigned at the moment of construction but may be assigned
	 * additionally (see {@link AbstractWindow#activate()}).
	 */
	public FilteredItemsSelectionDialog() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Openable getDefaultOpenAction() {
		return null;
	}

	/**
	 * Click 'OK' button.
	 */
	public void ok() {
		WidgetIsFound openButtonTest = new WidgetIsFound(org.eclipse.swt.widgets.Button.class, this.getControl(),
				new WithMnemonicTextMatcher("Open"));
		
		
		Button button;
		if(openButtonTest.test()){
			button = new PushButton((org.eclipse.swt.widgets.Button)openButtonTest.getResult()); //photon changed button text
		} else {
			button = new OkButton(this);	
		}
		button.click();
		new WaitWhile(new WindowIsAvailable(this));
	}

	/**
	 * Click 'Cancel' button.
	 */
	public void cancel() {
		new CancelButton(this).click();
		new WaitWhile(new WindowIsAvailable(this));
	}

	/**
	 * Get filter expression.
	 * 
	 * @return text from search box
	 */
	public void getFilterText() {
		new DefaultText(this).getText();
	}

	/**
	 * Set filter expression.
	 */
	@SuppressWarnings("restriction")
	public void setFilterText(String text) {
		new DefaultText(this).setText(text);
		new WaitWhile(new JobIsRunning(
				new WithMnemonicTextMatcher(WorkbenchMessages.FilteredItemsSelectionDialog_jobLabel), false), false);
	}

	/**
	 * Get results table.
	 * 
	 * @return table containing results of the search
	 */
	public Table getResultsTable() {
		return new DefaultTable(this);
	}

	/**
	 * Selects items in results table.
	 * 
	 * @param items
	 *            texts of items to select
	 * @see {@link Table#select(String...)}
	 */
	public void selectItem(String... items) {
		new DefaultTable(this).select(items);
	}

	/**
	 * Selects items in results table.
	 * 
	 * @param indices
	 *            row indices to select
	 * @see {@link Table#select(int...)}
	 */
	public void selectItem(int... indices) {
		new DefaultTable(this).select(indices);
	}
}
