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
package org.jboss.reddeer.eclipse.ui.ide;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.table.DefaultTableItem;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Class representing ExtendedFileEditorsPreferencePage (Preferences -> General
 * -> Editors -> File Associations). This preference page is used for changing
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
     */
    public void removeFileType(String fileType) {
	selectFileType(fileType);
	new PushButton("Remove").click();
    }

    /**
     * Returns list of editors for currently selected file type.
     * 
     * @return
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
     * @return
     */
    public List<String> getAssociatedEditorForFileType(String fileType) {
	selectFileType(fileType);
	return getAssociatedEditors();
    }

    /**
     * Selects given editor.
     * 
     * @param editor
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
     */
    public void makeEditorDefault(String editor) {
	selectAssociatedEditor(editor);
	new PushButton("Default").click();
    }

    /**
     * Adds new internal editor for currently selected file type.
     * 
     * @param editor
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
     */
    public void removeAssociatedEditor(String editor) {
	selectAssociatedEditor(editor);
	new PushButton(1, new WithMnemonicTextMatcher("Remove")).click();
    }
}
