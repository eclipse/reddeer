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
package org.eclipse.reddeer.eclipse.ui.ide;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Class representing ExtendedFileEditorsPreferencePage (Preferences &gt; General
 * &gt; Editors &gt; File Associations). This preference page is used for changing
 * associated editors for different file types.
 * 
 * 
 * @author rhopp
 *
 */
public class ExtendedFileEditorsPreferencePage extends PreferencePage {

	public ExtendedFileEditorsPreferencePage() {
		super("General", "Editors", "File Associations");
	}

	/**
	 * Returns all file types.
	 * 
	 * @return all file types.
	 */
	public List<String> getFileTypes() {
		List<String> resultList = new ArrayList<String>();
		DefaultTable fileTypesTable = new DefaultTable();
		List<TableItem> fileTypeItems = fileTypesTable.getItems();
		for (TableItem tableItem : fileTypeItems) {
			resultList.add(tableItem.getText());
		}
		return resultList;
	}

	/**
	 * Selects desired file type.
	 * 
	 * @param fileType
	 *            file type
	 */
	public void selectFileType(String fileType) {
		DefaultTable fileTypesTable = new DefaultTable();
		TableItem item = fileTypesTable.getItem(fileType);
		item.select();
	}

	/**
	 * Adds new file type.
	 * 
	 * @param fileType
	 *            file type
	 */
	public void addFileType(String fileType) {
		new PushButton("Add...").click();
		new DefaultShell("Add File Type");
		new LabeledText("File type:").setText(fileType);
		new OkButton().click();
	}

	/**
	 * Removes given file type.
	 * 
	 * @param fileType
	 *            file type
	 */
	public void removeFileType(String fileType) {
		selectFileType(fileType);
		new PushButton("Remove").click();
	}

	/**
	 * Returns list of editors for currently selected file type.
	 * 
	 * @return list of associated editors
	 */
	public List<String> getAssociatedEditors() {
		List<String> resultList = new ArrayList<String>();
		DefaultTable editorsTable = new DefaultTable(1);
		List<TableItem> editors = editorsTable.getItems();
		for (TableItem editorTableItem : editors) {
			resultList.add(editorTableItem.getText());
		}
		return resultList;
	}

	/**
	 * Returns list of editors for given file type.
	 * 
	 * @param fileType
	 *            file type
	 * @return list of associated editor to the file type
	 */
	public List<String> getAssociatedEditorForFileType(String fileType) {
		selectFileType(fileType);
		return getAssociatedEditors();
	}

	/**
	 * Selects given editor.
	 * 
	 * @param editor
	 *            associated editor to select
	 */
	public void selectAssociatedEditor(String editor) {
		DefaultTable editorsTable = new DefaultTable(1);
		TableItem item = null;
		try {
			item = editorsTable.getItem(editor);
		} catch (CoreLayerException ex) {
			// the item was not found. Try to find with regex ".*" appendix.
			item = new DefaultTableItem(editorsTable, new WithMnemonicTextMatcher(new RegexMatcher(editor + ".*")));
		}
		item.select();
	}

	/**
	 * Makes given editor default for currently selected file type.
	 * 
	 * @param editor
	 *            editor to make it default
	 */
	public void makeEditorDefault(String editor) {
		selectAssociatedEditor(editor);
		new PushButton("Default").click();
	}

	/**
	 * Adds new internal editor for currently selected file type.
	 * 
	 * @param editor
	 *            associated editor to add
	 */
	public void addAssociatedEditor(String editor) {
		new PushButton(1, new WithMnemonicTextMatcher("Add...")).click();
		new DefaultShell("Editor Selection");
		new DefaultTreeItem(editor).select();
		new OkButton().click();
	}

	/**
	 * Removes given editor for currently selected file type.
	 * 
	 * @param editor
	 *            associated editor to remove
	 */
	public void removeAssociatedEditor(String editor) {
		selectAssociatedEditor(editor);
		new PushButton(1, new WithMnemonicTextMatcher("Remove")).click();
	}
}
