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

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

public class CustomCompletionProcessor implements IContentAssistProcessor{

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		ICompletionProposal[] proposals = new ICompletionProposal[3];
		proposals[0] = getProposal("a");
		proposals[1] = getProposal("b");
		proposals[2] = getProposal("c");
		return proposals;
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ICompletionProposal getProposal(String proposalText){
		IContextInformation ci = new IContextInformation(){

			@Override
			public String getContextDisplayString() {
				return proposalText;
			}

			@Override
			public Image getImage() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getInformationDisplayString() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		
		ICompletionProposal proposal = new ICompletionProposal(){

			@Override
			public void apply(IDocument document) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Point getSelection(IDocument document) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getAdditionalProposalInfo() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getDisplayString() {
				return proposalText;
			}

			@Override
			public Image getImage() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IContextInformation getContextInformation() {
				return ci;
			}
			
		};
		return proposal;
		
	}

}
