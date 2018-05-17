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
package org.eclipse.reddeer.logparser.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.editors.text.TextEditor;

public class ReadOnlyTextEditor extends TextEditor {
	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing it's read-only
	}

	@Override
	public void doSaveAs() {
		// do nothing it's read-only
	}
	
	@Override
	public boolean isDirty() {
		return false;
	}
	
	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
}