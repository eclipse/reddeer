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

import org.eclipse.jdt.ui.text.IJavaPartitions;
import org.eclipse.jdt.ui.text.JavaSourceViewerConfiguration;
import org.eclipse.jdt.ui.text.JavaTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.ITextEditor;

public class CustomSourceViewerConfiguration extends JavaSourceViewerConfiguration{			

	public CustomSourceViewerConfiguration(JavaTextTools tools, IPreferenceStore preferenceStore, ITextEditor editor) {
		super( tools.getColorManager(), preferenceStore, editor, IJavaPartitions.JAVA_PARTITIONING );
	}
	
	public IContentAssistProcessor getContentAssistantProcessor() {
		return new CustomCompletionProcessor();
	}
	
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {

		ContentAssistant assistant = new ContentAssistant();
		assistant.setContentAssistProcessor( getContentAssistantProcessor(), IDocument.DEFAULT_CONTENT_TYPE );
		assistant.setContextInformationPopupOrientation( IContentAssistant.CONTEXT_INFO_ABOVE );
		assistant.setInformationControlCreator( getInformationControlCreator( sourceViewer ) );

		return assistant;
	}


}
