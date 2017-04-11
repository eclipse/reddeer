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
package org.eclipse.reddeer.workbench.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.workbench.api.Editor;

/**
 * Wait condition for checking whether editor is dirty
 * @author rawagner
 *
 */
public class EditorIsDirty extends AbstractWaitCondition {
	
	private Editor editor;
	
	public EditorIsDirty(Editor editor) {
		this.editor = editor;
	}

	@Override
	public boolean test() {
		return editor.isDirty();
	}
	
	@Override
	public String errorMessageWhile() {
		return "Editor '"+editor.getTitle()+"' is dirty";
	}
	

	@Override
	public String errorMessageUntil() {
		return "Editor '"+editor.getTitle()+"' is not dirty";
	}
	
	

}