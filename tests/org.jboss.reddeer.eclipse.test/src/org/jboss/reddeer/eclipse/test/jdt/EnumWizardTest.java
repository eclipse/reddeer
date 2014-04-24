package org.jboss.reddeer.eclipse.test.jdt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.NewEnumWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewEnumWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.junit.Test;

@CleanWorkspace
public class EnumWizardTest extends RedDeerTest {
	
	@Test
	public void createEnumClass(){
		NewJavaProjectWizardDialog jp = new NewJavaProjectWizardDialog();
		jp.open();
		jp.getFirstPage().setProjectName("EnumProject");
		jp.finish();
		
		NewEnumWizardDialog ed = new NewEnumWizardDialog();
		ed.open();
		NewEnumWizardPage ep = ed.getFirstPage();
		ep.setName("MyEnum");
		ep.setPackage("enumPackage");
		assertTrue(ep.isPublicModifier());
		assertFalse(ep.isGenerateComments());
		ed.finish();
		TextEditor te = new TextEditor("MyEnum.java");
		assertTrue(te.getText().contains("public enum MyEnum"));
		te.close();
	}

}
