/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
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