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

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.logparser.model.ParseRule;

public class ParseRuleDialog extends Dialog{
	
	private ParseRule parseRule;
	private List<ParseRule> existingParseRules;
	private boolean isAdd = false;
	
	private Text txName;
	private Text txDescritpion;
	private Text txIncludeRegex;
	private Text txExcludeRegex;
	private Text txIndent;
	private Text txPrefix;
	private Text txDisplayLinesBefore;
	private Text txDisplaylinesAfter;
	
	public ParseRuleDialog(Shell parentShell , ParseRule parseRule , List<ParseRule> existingParseRules) {
		super(parentShell);
		this.parseRule = parseRule != null ? parseRule : new ParseRule();
		this.existingParseRules = existingParseRules;
		this.isAdd = parseRule.getName() == null || parseRule.getName().length() == 0; 
		setShellStyle(getShellStyle() | SWT.SHELL_TRIM);
	}
	@Override
	protected Control createDialogArea (Composite parent){
		Composite container = (Composite)super.createDialogArea(parent);
		container.setLayout(new GridLayout(2,false));
		
		GridData textLayoutData = new GridData (GridData.FILL_HORIZONTAL);
		textLayoutData.widthHint = 350;
		Label label = new Label(container, SWT.NONE);
		label.setText("Name:");
		txName = new Text(container, SWT.BORDER);
		txName.setLayoutData(textLayoutData);
		txName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setOKButtonSensitivity();
			}
		});
		label = new Label(container, SWT.BORDER);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		label.setText("Description:");
		txDescritpion = new Text(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData multiTextLayoutData = new GridData (GridData.FILL_HORIZONTAL);
		multiTextLayoutData.widthHint = textLayoutData.widthHint;
		multiTextLayoutData.heightHint = 70;
		txDescritpion.setLayoutData(multiTextLayoutData);
		label = new Label(container, SWT.NONE);
		label.setText("Include Regex:");
		txIncludeRegex = new Text(container, SWT.BORDER);
		txIncludeRegex.setLayoutData(textLayoutData);
		label = new Label(container, SWT.NONE);
		label.setText("Exlude Regex:");
		txExcludeRegex = new Text(container, SWT.BORDER);
		txExcludeRegex.setLayoutData(textLayoutData);
		label = new Label(container, SWT.NONE);
		label.setText("Indent:");
		txIndent = new Text(container, SWT.BORDER);
		txIndent.setLayoutData(textLayoutData);
		label = new Label(container, SWT.NONE);
		label.setText("Prefix:");
		txPrefix = new Text(container, SWT.BORDER);
		txPrefix.setLayoutData(textLayoutData);
		label = new Label(container, SWT.NONE);
		label.setText("Display lines before:");
		txDisplayLinesBefore = new Text(container, SWT.BORDER);
		txDisplayLinesBefore.setLayoutData(textLayoutData);
		label = new Label(container, SWT.NONE);
		label.setText("Display lines after:");
		txDisplaylinesAfter = new Text(container, SWT.BORDER);
		txDisplaylinesAfter.setLayoutData(textLayoutData);
		
		initContent();
		
		return container;
	}
	
	protected void configureShell (Shell newShell){
		super.configureShell(newShell);
		newShell.setText((isAdd ? "Add" :"Edit") + " Parse Rule");
	}
	
	public ParseRule getParseRule() {
		return this.parseRule;
	}
	
	@Override
	protected void okPressed() {
		saveContent();
		super.okPressed();
	}
	
	private void initContent(){
		txName.setText(parseRule.getName());
		txDescritpion.setText(parseRule.getDescription());
		txIncludeRegex.setText(parseRule.getIncludeRegex());
		txExcludeRegex.setText(parseRule.getExcludeRegex());
		txIndent.setText(String.valueOf(parseRule.getIndent()));
		txPrefix.setText(parseRule.getPrefix());
		txDisplayLinesBefore.setText(String.valueOf(parseRule.getDisplayLinesBefore()));
		txDisplaylinesAfter.setText(String.valueOf(parseRule.getDisplaylinesAfter()));
	}
	
	private void setOKButtonSensitivity(){
		boolean enabled = false;
		if (isAdd){
			enabled = txName.getText().length() > 0 && isNameUnique(txName.getText());
		}
		else{
			enabled = txName.getText().length() > 0 && 
				(txName.getText().equalsIgnoreCase(parseRule.getName()) || isNameUnique(txName.getText()));
		}
		
		Button btnOK = getButton(IDialogConstants.OK_ID); 
		if (btnOK != null){
			btnOK.setEnabled(enabled);
		}
	}
	
	private boolean isNameUnique (String name){
		boolean isUnique = true;
		if (existingParseRules != null && existingParseRules.size() > 0){
			Iterator<ParseRule> itParseRule = existingParseRules.iterator();
			while (isUnique && itParseRule.hasNext()){
				if (name.equalsIgnoreCase(itParseRule.next().getName())){
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
	
	private void saveContent(){
		parseRule.setName(txName.getText());
		parseRule.setDescription(txDescritpion.getText());
		parseRule.setIncludeRegex(txIncludeRegex.getText());
		parseRule.setExcludeRegex(txExcludeRegex.getText());
		parseRule.setIndent(Integer.parseInt(txIndent.getText()));
		parseRule.setPrefix(txPrefix.getText());
		parseRule.setDisplayLinesBefore(Integer.parseInt(txDisplayLinesBefore.getText()));
		parseRule.setDisplaylinesAfter(Integer.parseInt(txDisplaylinesAfter.getText()));
	}
}
