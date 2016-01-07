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
package org.jboss.reddeer.eclipse.ui.dialogs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * 
 * Preference page for managing File Associations.  
 * 
 * @author Vlado Pakan
 *
 */
public class FileEditorsPreferencePage extends PreferencePage {

	public static final String PAGE_NAME = "File Associations";

	private static final Logger log = Logger.getLogger(FileEditorsPreferencePage.class);
	
	/**
	 * Constructs the preference page with "Server" > {@value #PAGE_NAME}.
	 */
	public FileEditorsPreferencePage() {
		super(new String[] {"General", "Editors" ,PAGE_NAME});
	}
	
	/**
	 * Returns true if all file types are defined.
	 *
	 * @param fileTypes - file types to be defined
	 * @return true, if successful
	 */
	public boolean containsFileTypes(String... fileTypes){
		boolean contains = true;
		int index = 0;
		while (index < fileTypes.length && contains){
			if (!getFileTypesTable().containsItem(fileTypes[index])){
				contains = false;
			}
			index++;
		}
		return contains;
	}
	
	/**
	 * Returns true if all specified associated editors are defined.
	 *
	 * @param associatedEditors - associated editors to be defined
	 * @return true, if successful
	 */
	public boolean containsAssociatedEditors(String... associatedEditors){
		boolean contains = true;
		int index = 0;
		List<String> definedEditors = getAssociatedEditors();
		while (index < associatedEditors.length && contains){
			if (!definedEditors.contains(associatedEditors[index])){
				contains = false;
			}
			index++;
		}
		return contains;
	}
	
	/**
	 * Selects file types. Peviously selected file types are deselected.
	 *
	 * @param fileTypes the file types
	 */
	public void selectFileTypes(String... fileTypes){
		Table tbFileTypes = getFileTypesTable();
		tbFileTypes.deselectAll();
		tbFileTypes.select(fileTypes);
	}
	/**
	 * Selects associated editors. Previously selected editors are deselected. 
	 * @param associatedEditors - associated editors to be selected
	 */
	public void selectAssociatedEditors(String... associatedEditors){
		LinkedList<TableItem> lsEditorsToSelect = new LinkedList<TableItem>();
		for (String associatedEditor : associatedEditors){
			TableItem tiEditor = findAssociatedEditor(associatedEditor);
			if (tiEditor == null){
				throw new EclipseLayerException("Unable to select associated editor with label: '"
					+ associatedEditor + "'");
			}
			lsEditorsToSelect.add(tiEditor);
		}
		String[] editorsToSelect = new String[lsEditorsToSelect.size()];
		int index = 0;
		for (TableItem editorToSelect : lsEditorsToSelect){
			editorsToSelect[index] = editorToSelect.getText();
			index++;
		}
		Table tbAssocitedEditrs = getAssociatedEditorsTable();
		tbAssocitedEditrs.deselectAll();
		tbAssocitedEditrs.select(editorsToSelect);
	}
	
	/**
	 * Returns defined file types.
	 *
	 * @return the defined file types
	 */
	public List<String> getDefinedFileTypes() {
		
		LinkedList<String> result = new LinkedList<String>();
		
		for (TableItem tableItem : getFileTypesTable().getItems()) {
			result.add(tableItem.getText());
		}
		
		return result;
	}
	
	/**
	 * Returns associated editors names without 'default' or 'locked by' texts.
	 *
	 * @return the associated editors
	 */
	public List<String> getAssociatedEditors() {
		
		LinkedList<String> result = new LinkedList<String>();
		
		for (TableItem tableItem : getAssociatedEditorsTable().getItems()) {
			result.add(FileEditorsPreferencePage.stripAssociatedEditorLabel(tableItem.getText()));
		}
		
		return result;
	}
	
	/**
	 * Returns associated editors complete labels including without 'default' or 'locked by' texts.
	 *
	 * @return the associated editors lables
	 */
	public List<String> getAssociatedEditorsLables() {
		
		LinkedList<String> result = new LinkedList<String>();
		
		for (TableItem tableItem : getAssociatedEditorsTable().getItems()) {
			result.add(tableItem.getText());
		}
		
		return result;
	}
	
	/**
	 * Adds file type.
	 *
	 * @param fileType - file type name to add
	 */
	public void addFileType (String fileType){
		log.debug("Adding file type: '" + fileType + "'");
		new PushButton("Add...").click();
		new DefaultShell("Add File Type");
		new LabeledText("File type:").setText(fileType);
		new OkButton().click();
	}
	
	/**
	 * Adds associated editor.
	 *
	 * @param associatedEditor - associated editor name to add
	 * @param selectInternalEditor the select internal editor
	 */
	public void addAssociatedEditor (String associatedEditor , boolean selectInternalEditor){
		log.debug("Adding associated editor: '" + associatedEditor + "'");
		new PushButton(1, new WithMnemonicTextMatcher("Add...")).click();
		new DefaultShell("Editor Selection");
		if (selectInternalEditor){
			new RadioButton("Internal editors").click();
		}
		else{
			new RadioButton("External programs").click();
		}
		new DefaultTreeItem(associatedEditor).select();;
		new OkButton().click();
	}
	
	/**
	 * Removes file type.
	 *
	 * @param fileType - file type name to remove
	 */
	public void removeFileType (String fileType){
		selectFileTypes(fileType);
		log.debug("Removing file type '" + fileType + "'");
		new PushButton("Remove").click();
	}
	
	/**
	 * Removes associated editor.
	 *
	 * @param associatedEditor - associated editor name to remove
	 */
	public void removeAssociatedEditor (String associatedEditor){
		selectAssociatedEditors(associatedEditor);
		log.debug("Removing associated editor '" + associatedEditor + "'");
		new PushButton(1, new WithMnemonicTextMatcher("Remove")).click();
	}
	
	/**
	 * Sets associated editor as default.
	 *
	 * @param associatedEditor - name of associated editor to be set as default
	 */
	public void setAssociatedEditorAsDefault (String associatedEditor) {
		selectAssociatedEditors(associatedEditor);
		log.debug("Marking associated editor '" + associatedEditor + "' as default");
		new PushButton("Default").click();
	}
	/**
	 * Returns File Types Table.
	 * @return
	 */
	private Table getFileTypesTable(){
		return new DefaultTable(0);
	}
	
	/**
	 * Returns Associated Editors Table. 
	 * @return
	 */
	private Table getAssociatedEditorsTable(){
		return new DefaultTable(1);
	}
	/**
	 * Strip associated editor label from '(Default)' and '(Locked by...) strings
	 * @param editorLabel
	 * @return
	 */
	private static String stripAssociatedEditorLabel(String editorLabel){
		String result = editorLabel;
		int pos = result.indexOf("(default)");
		if (pos >= 0) {
			result = result.substring(0, pos - 1);
		}
		pos = result.indexOf("(locked by");
		if (pos >= 0) {
			result = result.substring(0, pos - 1);
		}
		return result.trim();
	}
	/**
	 * Returns table item from associated editor table with label
	 * specified by associatedEditor. Returns null in case table item
	 * with specified label doesn't exist
	 * @param associatedEditor
	 * @return
	 */
	private TableItem findAssociatedEditor (String associatedEditor){
		TableItem result = null;
		List<TableItem> lsAssociatedEditors = getAssociatedEditorsTable().getItems();
		Iterator<TableItem> itAssociatedEditors = lsAssociatedEditors.iterator();
		while (result ==  null && itAssociatedEditors.hasNext()){
			TableItem tiEditor = itAssociatedEditors.next();
			if (FileEditorsPreferencePage.stripAssociatedEditorLabel(tiEditor.getText())
					.equalsIgnoreCase(associatedEditor)){
				result = tiEditor;
			}
		}
		return result;
	}
}
