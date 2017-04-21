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
package org.eclipse.reddeer.logparser.preferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.reddeer.logparser.dialogs.ParseRuleDialog;
import org.eclipse.reddeer.logparser.LogParserActivator;
import org.eclipse.reddeer.logparser.model.ParseRule;

public class LogParserPreferecesPage extends PreferencePage implements IWorkbenchPreferencePage {

	private TableViewer parseRuleviewer;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnRemove;
	private Button btnDuplicate;
	private List<ParseRule> currentParseRules;
	private List<ParseRule> originalParseRules;

	public LogParserPreferecesPage() {
		super();
		noDefaultButton();
		setPreferenceStore(LogParserActivator.getDefault().getPreferenceStore());
		setDescription("Add, remove or edit RedDeer Log Parser rules");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new GridLayout(2, false));
		pageComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		parseRuleviewer = new TableViewer(pageComposite,
				SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		parseRuleviewer.setContentProvider(new ArrayContentProvider());
		parseRuleviewer.setLabelProvider(new ParseRuleLabelProvider());
		parseRuleviewer.setSorter(new ParseRuleNameSorter());
		currentParseRules = LogParserPreferencesPageModel.getParseRules();
		originalParseRules = new ArrayList<ParseRule>(currentParseRules);
		parseRuleviewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				setButtonsSensitivity();
			}
		});
		Table table = parseRuleviewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		GridData tableGridData = new GridData(GridData.FILL_BOTH);
		tableGridData.verticalSpan = 4;
		table.setLayoutData(tableGridData);
		String[] headerNames = new String[] { "Rule Name", "Description", "Include Regex", "ExcludeRegex" };
		int[] columnAligments = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT };
		for (int index = 0; index < headerNames.length; index++) {
			TableColumn tc = new TableColumn(table, columnAligments[index]);
			tc.setText(headerNames[index]);
		}
		parseRuleviewer.setInput(currentParseRules);
		packParseRuleColumns();
		createButtons(pageComposite);
		setButtonsSensitivity();
		pageComposite.pack();

		return pageComposite;
	}

	private void createButtons(Composite parent) {

		GridData buttonGridData = new GridData(SWT.CENTER, SWT.BEGINNING, false, false);
		buttonGridData.widthHint = 120;
		buttonGridData.verticalAlignment = SWT.BEGINNING;

		btnAdd = new Button(parent, SWT.PUSH);
		btnAdd.setLayoutData(buttonGridData);
		btnAdd.setText("&Add...");
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addRule();
			}
		});

		btnEdit = new Button(parent, SWT.PUSH);
		btnEdit.setText("&Edit...");
		btnEdit.setLayoutData(buttonGridData);
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editRule();
			}
		});

		btnDuplicate = new Button(parent, SWT.PUSH);
		btnDuplicate.setText("Dupli&cate...");
		btnDuplicate.setLayoutData(buttonGridData);
		btnDuplicate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				duplicateRule();
			}
		});

		btnRemove = new Button(parent, SWT.PUSH);
		btnRemove.setText("&Remove");
		btnRemove.setLayoutData(buttonGridData);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeRule();
			}
		});
	}

	private void setButtonsSensitivity() {
		btnAdd.setEnabled(true);
		boolean enable = true;
		if (parseRuleviewer.getSelection().isEmpty()) {
			enable = false;
		}
		btnEdit.setEnabled(enable);
		btnRemove.setEnabled(enable);
		btnDuplicate.setEnabled(enable);
	}

	@Override
	public boolean performOk() {
		LogParserPreferencesPageModel.saveParseRules(currentParseRules);
		boolean result = super.performOk();
		LogParserActivator.notifyAllPropertyPageListeners(
			new PropertyChangeEvent(this, "RULES", originalParseRules, currentParseRules));
		return result;
	}	

	private void addRule() {
		ParseRuleDialog parseRuleDialog = new ParseRuleDialog(parseRuleviewer.getControl().getShell(), new ParseRule(),
				currentParseRules);
		if (parseRuleDialog.open() == InputDialog.OK) {
			ParseRule newParseRule = parseRuleDialog.getParseRule();
			parseRuleviewer.add(newParseRule);
			currentParseRules.add(newParseRule);
		}
	}

	private void editRule() {
		ParseRule updParseRule = ((ParseRule) parseRuleviewer.getStructuredSelection().getFirstElement()).clone();
		ParseRule updModelParseRule = currentParseRules.get(currentParseRules.indexOf(updParseRule));
		ParseRuleDialog parseRuleDialog = new ParseRuleDialog(parseRuleviewer.getControl().getShell(), updParseRule,
				currentParseRules);
		if (parseRuleDialog.open() == InputDialog.OK) {
			ParseRule.copyFields(parseRuleDialog.getParseRule(), updModelParseRule);
			parseRuleviewer.setInput(currentParseRules);
			parseRuleviewer.setSelection(new StructuredSelection(updModelParseRule));
		}
	}

	private void removeRule() {
		ParseRule removeParseRule = ((ParseRule) parseRuleviewer.getStructuredSelection().getFirstElement()).clone();
		parseRuleviewer.remove(removeParseRule);
		currentParseRules.remove(removeParseRule);
	}

	private void duplicateRule() {
		ParseRule duplicateParseRule = ((ParseRule) parseRuleviewer.getStructuredSelection().getFirstElement()).clone();
		duplicateParseRule.setName(getDuplicateUniqueName(duplicateParseRule.getName()));
		ParseRuleDialog parseRuleDialog = new ParseRuleDialog(parseRuleviewer.getControl().getShell(), duplicateParseRule,
				currentParseRules);
		if (parseRuleDialog.open() == InputDialog.OK) {
			ParseRule newParseRule = parseRuleDialog.getParseRule();
			parseRuleviewer.add(newParseRule);
			currentParseRules.add(newParseRule);
		}
	}

	private String getDuplicateUniqueName(String origRuleName) {
		Pattern regex = Pattern.compile(" \\(\\d+\\)");
		Matcher m = regex.matcher(origRuleName);
		String nameIndex = null;
		while (m.find()) {
			nameIndex = m.group();
		}
		int newNameIndex = 0;
		String origRuleNameWithoutNumber = origRuleName;
		if (nameIndex == null) {
			newNameIndex = 1;
		} else {
			nameIndex = nameIndex.substring(2, nameIndex.length() - 1);
			newNameIndex = Integer.parseInt(nameIndex) + 1;
			origRuleNameWithoutNumber = origRuleName.substring(0, origRuleName.lastIndexOf(" ("));
		}

		boolean nameIsUnique = false;
		String newParseRuleName = "";
		List<ParseRule> parseRules = currentParseRules;
		while (!nameIsUnique) {
			newParseRuleName = origRuleNameWithoutNumber + " (" + newNameIndex + ")";
			boolean newParseRuleNameExists = false;
			Iterator<ParseRule> itParseRule = parseRules.iterator();
			while (!newParseRuleNameExists && itParseRule.hasNext()) {
				if (itParseRule.next().getName().equalsIgnoreCase(newParseRuleName)) {
					newParseRuleNameExists = true;
				}
			}
			if (newParseRuleNameExists) {
				newNameIndex++;
			} else {
				nameIsUnique = true;
			}
		}

		return newParseRuleName;

	}
	
	private void packParseRuleColumns(){
		for (TableColumn tableColumn : parseRuleviewer.getTable().getColumns()){
			tableColumn.pack();
		}
	}

}