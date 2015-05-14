package org.jboss.reddeer.snippet.test.example;

import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.NewJavaClassWizardPage;
import org.junit.Test;

public class JavaWizardExample {

	@Test
	public void testJavaWizardDialogAndPage() {
		NewJavaClassWizardDialog wizard = new NewJavaClassWizardDialog();
		wizard.open();

		NewJavaClassWizardPage page = new NewJavaClassWizardPage();
		page.setName("MyClass");
		page.setPackage("org.reddeer.example");

		wizard.finish();
	}
	
}
