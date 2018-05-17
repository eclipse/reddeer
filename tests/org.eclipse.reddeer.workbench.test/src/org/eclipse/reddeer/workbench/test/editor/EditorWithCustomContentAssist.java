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
package org.eclipse.reddeer.workbench.test.editor;

import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jdt.ui.text.JavaTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;

public class EditorWithCustomContentAssist extends TextEditor{

	
	public EditorWithCustomContentAssist() {
		IPreferenceStore store = new ChainedPreferenceStore(new IPreferenceStore[] {
				PreferenceConstants.getPreferenceStore(),
				EditorsUI.getPreferenceStore()});
		setSourceViewerConfiguration(new CustomSourceViewerConfiguration(new JavaTextTools(PreferenceConstants.getPreferenceStore()), store, this));
	}
}
