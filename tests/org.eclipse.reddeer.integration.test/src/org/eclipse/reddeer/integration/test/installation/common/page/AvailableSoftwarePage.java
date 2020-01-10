/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.integration.test.installation.common.page;

import java.util.List;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.integration.test.installation.common.dialog.InstallNewSoftwareDialog;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;

public class AvailableSoftwarePage extends ValidatedWizardPage {

	public static final String PAGE_TITLE = "Available Software";

	public AvailableSoftwarePage(InstallNewSoftwareDialog installNewSoftwareDialog) {
		super(installNewSoftwareDialog, PAGE_TITLE);
	}

	public void addUpdateSite(String source) {
		new PushButton(referencedComposite, "Add...").click();
		new WaitUntil(new ShellIsAvailable("Add Repository"), TimePeriod.LONG);
		new LabeledText("Location:").setText(source);
		try {
			new OkButton().click();
		} catch (SWTLayerException | CoreLayerException e) {
			new PushButton(new WithTextMatcher(new RegexMatcher(".*A.*dd.*"))).click();
		}

		new WaitWhile(new ShellIsAvailable("Add Repository"), TimePeriod.LONG);
		waitUntillUIsAreLoaded();
	}

	public void selectSoftware(String pattern) {
		RegexMatcher matcher = new RegexMatcher(pattern);

		for (TreeItem item : new DefaultTree(referencedComposite).getItems()) {
			String text = item.getText();

			if (matcher.matches(text)) {
				item.setChecked(true);
				log.info("Item " + item.getText() + " selected to be installed.");
			}
		}
	}

	public List<TableItem> getItems() {
		waitUntillUIsAreLoaded();
		return new DefaultTable(referencedComposite).getItems();
	}

	protected void waitUntillUIsAreLoaded() {
		new WaitWhile(new AbstractWaitCondition() {

			@Override
			public boolean test() {
				DefaultTree tree = new DefaultTree(referencedComposite);

				if (tree.getItems().size() == 0) {
					return true;
				}

				for (TreeItem item : tree.getItems()) {
					if (item.isDisposed() || item.getText().equals("Pending...")) {
						return true;
					}
				}

				return false;
			}

			@Override
			public String description() {
				return "Tree has no items.";
			}
		}, TimePeriod.LONG);
	}
}
