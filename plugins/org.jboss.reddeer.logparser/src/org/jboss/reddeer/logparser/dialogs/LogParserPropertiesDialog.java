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
package org.jboss.reddeer.logparser.dialogs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.logparser.model.LogData;
import org.jboss.reddeer.logparser.model.ParseRule;
import org.jboss.reddeer.logparser.preferences.ParseRuleNameSorter;

public class LogParserPropertiesDialog extends Dialog{
	
	private LogData logData;
	private List<ParseRule> definedParseRules;
	private List<ParseRule> availableParseRules;
	private List<ParseRule> selectedParseRules;
	private List<LogData> existingLogs;
	private boolean isAdd = false;
	private Text txLocation;
	private ListViewer availableRulesViewer;
	private ListViewer selectedRulesViewer;
	private Button btnAdd;
	private Button btnAddAll;
	private Button btnRemove;
	private Button btnRemoveAll;
	private Button btnMoveUp;
	private Button btnMoveDown;
	
	public LogParserPropertiesDialog(Shell parentShell, LogData logData, List<ParseRule> definedParseRules,
			List<LogData> existingLogs) {
		super(parentShell);
		this.logData = logData != null ? logData : new LogData();
		this.definedParseRules = definedParseRules != null ? definedParseRules : new ArrayList<ParseRule>();
		this.existingLogs = existingLogs;
		this.isAdd = logData.getLocation() == null || logData.getLocation().length() == 0;
		this.availableParseRules = new ArrayList<ParseRule>(this.definedParseRules);
		if (this.logData != null && logData.getParseRules() != null){
			this.availableParseRules.removeAll(logData.getParseRules());
			this.selectedParseRules = logData.getParseRules();
			this.selectedParseRules.retainAll(this.definedParseRules);
		}
		else{
			this.selectedParseRules = new ArrayList<ParseRule>();
		}		
		setShellStyle(getShellStyle() | SWT.SHELL_TRIM); 
	}
	@Override
	protected Control createDialogArea (Composite parent){
		Composite container = (Composite)super.createDialogArea(parent);
		container.setLayout(new GridLayout(1,false));
		
		Composite cmpLocation = new Composite(container, SWT.NONE);
		cmpLocation.setLayout(new GridLayout(3, false));
		cmpLocation.setLayoutData(new GridData (GridData.FILL_HORIZONTAL));
		Label label = new Label(cmpLocation, SWT.NONE);
		label.setText("Log location:");
		txLocation = new Text(cmpLocation, SWT.BORDER);
		txLocation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txLocation.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setOKButtonSensitivity();
			}
		});
		Button btnBrowse = new Button(cmpLocation, SWT.PUSH);
		GridData gdButton = new GridData ();
		gdButton.widthHint = 130;
		btnBrowse.setLayoutData(gdButton);
		btnBrowse.setText("Browse...");
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseForLog();
			}
		});
		
		Composite cmpRules = new Composite(container, SWT.NONE);
		cmpRules.setLayout(new GridLayout(4, false));
		cmpRules.setLayoutData(new GridData (GridData.FILL_BOTH));
		label = new Label(cmpRules, SWT.NONE);
		label.setText("Avaialable Rules");
		new Label(cmpRules, SWT.NONE);
		label = new Label(cmpRules, SWT.NONE);
		label.setText("Selected Rules");
		new Label(cmpRules, SWT.NONE);
		availableRulesViewer = new ListViewer(cmpRules, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		availableRulesViewer.setContentProvider(new ArrayContentProvider());
		availableRulesViewer.setSorter(new ParseRuleNameSorter());
		availableRulesViewer.setLabelProvider(new ParseRuleListLabelProvider ());
		availableRulesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				setButtonsSensitivity();
			}
		});
		availableRulesViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				addParseRules();				
			}
		});
		GridData gdListViewer = new GridData (GridData.FILL_BOTH);
		gdListViewer.widthHint = 300; 
		gdListViewer.heightHint = 400;
		availableRulesViewer.getControl().setLayoutData(gdListViewer);
		Composite cmpAddRemoveBtns = new Composite(cmpRules, SWT.NONE);
		cmpAddRemoveBtns.setLayout(new GridLayout(1,false));
		cmpAddRemoveBtns.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		btnAdd = new Button (cmpAddRemoveBtns, SWT.PUSH);
		btnAdd.setText("Add >>");
		btnAdd.setLayoutData(gdButton);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addParseRules();
			}
		});
		btnAddAll = new Button (cmpAddRemoveBtns, SWT.PUSH);
		btnAddAll.setText("Add all >>");
		btnAddAll.setLayoutData(gdButton);
		btnAddAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addAllParseRules();
			}
		});
		
		btnRemove = new Button (cmpAddRemoveBtns, SWT.PUSH);
		btnRemove.setText("<< Remove");
		btnRemove.setLayoutData(gdButton);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeParseRules();
			}
		});
		btnRemoveAll = new Button (cmpAddRemoveBtns, SWT.PUSH);
		btnRemoveAll.setText("<< Remove all");
		btnRemoveAll.setLayoutData(gdButton);
		btnRemoveAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeAllParseRules();
			}
		});
		
		selectedRulesViewer = new ListViewer(cmpRules, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		selectedRulesViewer.setContentProvider(new ArrayContentProvider());
		selectedRulesViewer.setLabelProvider(new ParseRuleListLabelProvider ());
		selectedRulesViewer.getControl().setLayoutData(gdListViewer);
		selectedRulesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				setButtonsSensitivity();
			}
		});
		selectedRulesViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				removeParseRules();				
			}
		});
		Composite cmpMoveUpDownBtns = new Composite(cmpRules, SWT.NONE);
		cmpMoveUpDownBtns.setLayout(new GridLayout(1,false));
		cmpMoveUpDownBtns.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		btnMoveUp = new Button (cmpMoveUpDownBtns, SWT.PUSH);
		btnMoveUp.setText("Move Up");
		btnMoveUp.setLayoutData(gdButton);
		btnMoveUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moveParseRulesUp();
			}
		});
		btnMoveDown = new Button (cmpMoveUpDownBtns, SWT.PUSH);
		btnMoveDown.setText("Move Down");
		btnMoveDown.setLayoutData(gdButton);
		btnMoveDown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				moveParseRulesDown();
			}
		});
		
		initContent();
		
		setButtonsSensitivity();
		
		return container;
	}
	
	protected void configureShell (Shell newShell){
		super.configureShell(newShell);
		newShell.setText((isAdd ? "Add" :"Edit") + " Log to parse");
	}
	
	public LogData getLogData() {
		return this.logData;
	}
	
	@Override
	protected void okPressed() {
		saveContent();
		super.okPressed();
	}
	
	private void initContent(){
		availableRulesViewer.setInput(this.availableParseRules);
		selectedRulesViewer.setInput(this.selectedParseRules);
		txLocation.setText(this.logData.getLocation());
	}
	
	private void setOKButtonSensitivity(){
		boolean enabled = false;
		if (isAdd){
			enabled = txLocation.getText().length() > 0 && isLocationUnique(txLocation.getText());
		}
		else{
			enabled = txLocation.getText().length() > 0 && 
				(txLocation.getText().equalsIgnoreCase(logData.getLocation()) || isLocationUnique(txLocation.getText()));
		}
		if (enabled){
			enabled = new File (txLocation.getText()).exists();
		}
		Button btnOK = getButton(IDialogConstants.OK_ID); 
		if (btnOK != null){
			btnOK.setEnabled(enabled);
		}
	}
	
	private boolean isLocationUnique (String location){
		boolean isUnique = true;
		if (existingLogs != null && existingLogs.size() > 0){
			Iterator<LogData> itLogData = existingLogs.iterator();
			while (isUnique && itLogData.hasNext()){
				if (location.equalsIgnoreCase(itLogData.next().getLocation())){
					isUnique = false;
				}
			}			
		}
			
		return isUnique;
	}
	@Override
	protected void createButtonsForButtonBar(Composite parent){
		super.createButtonsForButtonBar(parent);
		setOKButtonSensitivity();
	}
	
	private void browseForLog() {
		FileDialog fileDialog = new FileDialog(btnAdd.getShell(), SWT.OPEN);
		fileDialog.setFilterExtensions(new String[] {"*.log","*.txt","*.*" });
		fileDialog.setFilterNames(new String[] {"Log files (*.log)","Text files (*.txt)" , "All files (*.*)"});
		if (txLocation.getText().length() > 0){
			fileDialog.setFileName(txLocation.getText());
		}
		String fileName = fileDialog.open();
		if (fileName != null) {
			txLocation.setText(fileName);
		}
	}
	
	private void saveContent(){
		logData.setLocation(txLocation.getText());
		logData.setParseRules(selectedParseRules);
	}	
	
	private void addParseRules() {
		IStructuredSelection selection = availableRulesViewer.getStructuredSelection();
		if (selection != null && !selection.isEmpty()){
			Iterator<?> itSelection = selection.iterator();
			while (itSelection.hasNext()){
				ParseRule parseRule = (ParseRule)itSelection.next();
				selectedParseRules.add(parseRule);
				availableParseRules.remove(parseRule);
			}
			refreshParseRulesLists();
		}
	}
	
	private void addAllParseRules(){
		selectedParseRules.addAll(availableParseRules);
		availableParseRules.clear();
		refreshParseRulesLists();
	}
	
	private void removeParseRules (){
		IStructuredSelection selection = selectedRulesViewer.getStructuredSelection();
		if (selection != null && !selection.isEmpty()){
			Iterator<?> itSelection = selection.iterator();
			while (itSelection.hasNext()){
				ParseRule parseRule = (ParseRule)itSelection.next();
				availableParseRules.add(parseRule);
				selectedParseRules.remove(parseRule);
			}
			refreshParseRulesLists();
		}
	}
	
	private void removeAllParseRules(){
		availableParseRules.addAll(selectedParseRules);
		selectedParseRules.clear();
		refreshParseRulesLists();
	}
	
	private void moveParseRulesDown(){
		IStructuredSelection selection = selectedRulesViewer.getStructuredSelection();
		if (selection != null && !selection.isEmpty()){
			Iterator<?> itSelection = selection.iterator();
			ArrayList<Integer> indices = new ArrayList<Integer>();
			while (itSelection.hasNext()){
				ParseRule parseRule = (ParseRule)itSelection.next();
				indices.add(selectedParseRules.indexOf(parseRule));
			}
			Collections.sort(indices);
			Collections.reverse(indices);
			if (indices.get(0) < selectedParseRules.size() - 1){
				for (int index : indices){
					Collections.swap(selectedParseRules, index, index + 1);
				}
			}
			selectedRulesViewer.setInput(selectedParseRules);
			selectedRulesViewer.setSelection(selection);
			setButtonsSensitivity();
			selectedRulesViewer.getControl().setFocus();
		}
	}
	
	private void moveParseRulesUp(){
		IStructuredSelection selection = selectedRulesViewer.getStructuredSelection();
		if (selection != null && !selection.isEmpty()){
			Iterator<?> itSelection = selection.iterator();
			ArrayList<Integer> indices = new ArrayList<Integer>();
			while (itSelection.hasNext()){
				ParseRule parseRule = (ParseRule)itSelection.next();
				indices.add(selectedParseRules.indexOf(parseRule));
			}
			Collections.sort(indices);
			if (indices.get(0) > 0){
				for (int index : indices){
					Collections.swap(selectedParseRules, index - 1, index);
				}
			}
			selectedRulesViewer.setInput(selectedParseRules);
			selectedRulesViewer.setSelection(selection);
			setButtonsSensitivity();
			selectedRulesViewer.getControl().setFocus();
		}
	}
	
	private void refreshParseRulesLists(){
		selectedRulesViewer.setInput(selectedParseRules);
		availableRulesViewer.setInput(availableParseRules);
		setButtonsSensitivity();
	}
	
	private void setButtonsSensitivity() {
		if (availableRulesViewer.getSelection().isEmpty()){
			btnAdd.setEnabled(false);
		}
		else {
			btnAdd.setEnabled(true);
		}
		
		if (selectedRulesViewer.getSelection().isEmpty()){
			btnRemove.setEnabled(false);
			btnMoveDown.setEnabled(false);
			btnMoveUp.setEnabled(false);
		}
		else {
			btnRemove.setEnabled(true);
			org.eclipse.swt.widgets.List lsSelectedRules = selectedRulesViewer.getList();
			int[] selectedIndices = lsSelectedRules.getSelectionIndices();
			if (selectedIndices != null && selectedIndices.length > 0){
				int minIndex = selectedIndices[0];
				int maxIndex = selectedIndices[0];
				for (int index = 1 ; index < selectedIndices.length ; index++){
					if (minIndex > selectedIndices[index]){
						minIndex = selectedIndices[index];
					}
					if (maxIndex < selectedIndices[index]){
						maxIndex = selectedIndices[index];
					}
				}
				btnMoveDown.setEnabled(maxIndex < lsSelectedRules.getItemCount() - 1);
				btnMoveUp.setEnabled(minIndex > 0);
			}
		}
		
		if (availableRulesViewer.getList().getItemCount() > 0){
			btnAddAll.setEnabled(true);
		}
		else{
			btnAddAll.setEnabled(false);
		}
		
		if (selectedRulesViewer.getList().getItemCount() > 0){
			btnRemoveAll.setEnabled(true);
		}
		else{
			btnRemoveAll.setEnabled(false);
		}		
		
	}
	
	private class ParseRuleListLabelProvider extends LabelProvider {
		public Image getImage (Object element){
			return null;
		}
		public String getText (Object element){
			ParseRule parseRule = (ParseRule)element; 
			return parseRule.getName()
				+ " [" 
				+ (parseRule.getDescription() != null ? parseRule.getDescription() : "no descritpion")
				+ "]";
		}
	}
}
