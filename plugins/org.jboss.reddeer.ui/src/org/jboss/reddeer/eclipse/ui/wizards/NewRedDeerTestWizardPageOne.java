package org.jboss.reddeer.eclipse.ui.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
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
import org.eclipse.jface.resource.ImageDescriptor;
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

		setImageDescriptor(ImageDescriptor.createFromURL(FileLocator.find(
				Platform.getBundle(Activator.PLUGIN_ID), new Path(
						"resources/reddeer_icon.png"), null)));
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