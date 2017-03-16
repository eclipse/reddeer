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
package org.jboss.reddeer.eclipse.ui.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.junit.wizards.NewTestCaseWizardPageOne;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.jboss.reddeer.ui.Activator;

/**
 * First page of Red Deer Test Case wizard.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class NewRedDeerTestWizardPageOne extends NewTestCaseWizardPageOne {

	protected NewRedDeerTestWizardPageOne(NewRedDeerTestWizardPageTwo pageTwo) {
		super(pageTwo);
		setTitle("Red Deer Test Case");

		setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(Activator.REDDEER_ICON));
	}

	@Override
	public void init(IStructuredSelection selection) {
		super.init(selection);
		setJUnit4(true, false);
 		updateStatus(getStatusList());
	}
	
	@Override
	protected void createTypeMembers(IType type, ImportsManager imports, IProgressMonitor monitor)
			throws CoreException {
		super.createTypeMembers(type, imports, monitor);
		
		imports.addImport("org.junit.runner.RunWith");
		imports.addImport("org.jboss.reddeer.junit.runner.RedDeerSuite");
		
		addAnnotation(type);
	}

	private void addAnnotation(IType type) throws JavaModelException {
		ISourceRange range = type.getSourceRange();
		IBuffer buf = type.getCompilationUnit().getBuffer();
		
		char[] source = buf.getCharacters();
		IScanner scanner = ToolFactory.createScanner( 
				false, false, false, JavaCore.VERSION_1_5 );
		scanner.setSource( source );
		int offset = range.getOffset();
		
		try {
			int token = scanner.getNextToken();
			while ( token != ITerminalSymbols.TokenNameEOF ) {
				if (token == ITerminalSymbols.TokenNamepublic) {
					offset = scanner.getCurrentTokenStartPosition();
					break;
				}
				token = scanner.getNextToken();
			}
		} catch (InvalidInputException e) {
			Activator.log(e);
		}
		StringBuffer sb = new StringBuffer();
		
		sb.append("@RunWith(RedDeerSuite.class)").append(getPackageFragment().findRecommendedLineSeparator());
		
		buf.replace(offset, 0, sb.toString());
	}
}