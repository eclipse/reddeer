package org.jboss.reddeer.requirements.test.closeeditors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.closeeditors.CloseAllEditorsRequirement;
import org.jboss.reddeer.requirements.closeeditors.CloseAllEditorsRequirement.CloseAllEditors;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class CloseAllEditorsRequirementTest {

	private CloseAllEditorsRequirement requirement;

	@Before
	public void openEditors(){
		UntitledTextFileWizardDialog dialog = new UntitledTextFileWizardDialog();
		dialog.open();
		dialog.finish();

		dialog = new UntitledTextFileWizardDialog();
		dialog.open();
		dialog.finish();

		dialog = new UntitledTextFileWizardDialog();
		dialog.open();
		dialog.finish();
	}

	@Before
	public void setupRequirement(){
		requirement = new CloseAllEditorsRequirement();
		requirement.setDeclaration(createInstanceOfAnnotation());
	}

	@Test
	public void closeAllEditors(){
		new TextEditor();

		requirement.fulfill();

		try {
			new TextEditor();
			fail("All editors should be closed");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	private CloseAllEditors createInstanceOfAnnotation() {
		return new CloseAllEditors() {

			@Override
			public boolean save() {
				return false;
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				return CloseAllEditors.class;
			}
		};
	}

	private class UntitledTextFileWizardDialog extends NewWizardDialog {

		public UntitledTextFileWizardDialog() {
			super("General", "Untitled Text File");
		}
	}
}
