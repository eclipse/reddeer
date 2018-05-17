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
package org.eclipse.reddeer.eclipse.wst.xml.ui.tabletree;

import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;

/**
 * Represents the Source page of {@link XMLMultiPageEditorPart}.
 * 
 * @author Lucia Jelinkova
 *
 */
public class XMLSourcePage extends TextEditor {

	/**
	 * Creates instance from existing text editor reference.
	 *
	 * @param editor
	 *            the editor
	 */
	protected XMLSourcePage(ITextEditor editor) {
		super(editor);
	}

	@Override
	public XMLEditorFile getAssociatedFile() {
		return new XMLEditorFile(super.getAssociatedFile());
	}

}
