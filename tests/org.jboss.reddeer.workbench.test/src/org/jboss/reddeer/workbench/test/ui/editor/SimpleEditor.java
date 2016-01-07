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
package org.jboss.reddeer.workbench.test.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class SimpleEditor extends EditorPart{

	private Label contents;
	public Boolean dirty=false;
	private Button buttonDirty;
	private Button buttonSave;
	
	@Override
	public String getTitle() {
		if (getEditorInput()== null){
			return super.getTitle();
		}else{
			setPartName(getEditorInput().getName());
			return getEditorInput().getName();
		}
	}
	
	@Override
    public void createPartControl(Composite parent) {
       contents = new Label(parent, SWT.NONE);
       contents.setText("Minimal Editor");
       buttonDirty = new Button(parent, SWT.PUSH);
       buttonDirty.setText("Make Dirty");
       buttonDirty.addSelectionListener(new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			dirty = true;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
		}
       });
       buttonSave = new Button(parent, SWT.PUSH);
       buttonSave.setText("Save");
       buttonSave.addSelectionListener(new SelectionListener() {
   		
   		@Override
   		public void widgetSelected(SelectionEvent arg0) {
   			dirty = false;
   			firePropertyChange(IEditorPart.PROP_DIRTY);
   		}
   		
   		@Override
   		public void widgetDefaultSelected(SelectionEvent arg0) {
   		}
          });
    }


	@Override
	public void doSave(IProgressMonitor arg0) {
		dirty=false;
		
	}

	@Override
	public void doSaveAs() {
		dirty=false;
		
	}

	@Override
	public void init(IEditorSite arg0, IEditorInput arg1)
			throws PartInitException {
		setSite(arg0);
		setInput(arg1);
		firePropertyChange(IWorkbenchPartConstants.PROP_INPUT);
		setPartName(getEditorInput().getName());
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
		if (contents!=null){
			contents.setFocus();
		}		
	}


	public void setDirty(Boolean dirty) {
		this.dirty = dirty;
	}
	
	
}
