/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.jface.test.dialogs.impl;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TestingTitleAreaDialog extends TitleAreaDialog{
	
	public static final String ERROR_MESSAGE = "TitleAreaDialog error message";
	public static final String WARNING_MESSAGE = "TitleAreaDialog warning message";
	public static final String INFO_MESSAGE = "TitleAreaDialog info message";
	public static final String NONE_MESSAGE = "TitleAreaDialog none message";
	public static final String ERROR_MESSAGE_WITHOUT_PROVIDER = "TitleAreaDialog warning message without provider";
	public static final String TITLE = "TitleAreaDialog title";
	public static final String TEXT = "Testing TitleAreaDialog";
	public static final String DEFAULT_MESSAGE = "TitleAreaDialog message";

	public TestingTitleAreaDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	public void create() {
		super.create();
		setTitle(TITLE);
		setMessage(DEFAULT_MESSAGE);
		getShell().setText(TEXT);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);
        Button errorMessageButton = new Button(container, SWT.PUSH);
        errorMessageButton.addSelectionListener(new SelectionAdapter() {
        	
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		setErrorMessage(ERROR_MESSAGE_WITHOUT_PROVIDER);
        	}
		});
        errorMessageButton.setText(ERROR_MESSAGE_WITHOUT_PROVIDER);
        
        Button warningButton = new Button(container, SWT.PUSH);
        warningButton.addSelectionListener(new SelectionAdapter() {
        	
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		setMessage(WARNING_MESSAGE, IMessageProvider.WARNING);
        	}
		});
        
        warningButton.setText(WARNING_MESSAGE);
        
        Button infoButton = new Button(container, SWT.PUSH);
        infoButton.addSelectionListener(new SelectionAdapter() {
        	
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		setMessage(INFO_MESSAGE, IMessageProvider.INFORMATION);
        	}
		});
        
        infoButton.setText(INFO_MESSAGE);
        
        Button noneButton = new Button(container, SWT.PUSH);
        noneButton.addSelectionListener(new SelectionAdapter() {
        	
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		setMessage(NONE_MESSAGE, IMessageProvider.NONE);
        	}
		});
        
        noneButton.setText(NONE_MESSAGE);
        
        Button errorButton = new Button(container, SWT.PUSH);
        errorButton.addSelectionListener(new SelectionAdapter() {
        	
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		setMessage(ERROR_MESSAGE, IMessageProvider.ERROR);
        	}
		});
        
        errorButton.setText(ERROR_MESSAGE);
        return area;
	}
	
	

}
