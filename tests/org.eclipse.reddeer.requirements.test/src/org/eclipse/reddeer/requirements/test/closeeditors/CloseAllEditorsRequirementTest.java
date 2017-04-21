/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.requirements.test.closeeditors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;

import org.eclipse.reddeer.eclipse.ui.dialogs.NewWizard;
import org.eclipse.reddeer.eclipse.ui.dialogs.NewWizardSelectionPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.closeeditors.CloseAllEditorsRequirement;
import org.eclipse.reddeer.requirements.closeeditors.CloseAllEditorsRequirement.CloseAllEditors;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class CloseAllEditorsRequirementTest {

	private CloseAllEditorsRequirement requirement;

	@Before
	public void openEditors(){
		for(int i=0;i<3;i++){
			NewWizard newWizard = new NewWizard();
			newWizard.open();
			NewWizardSelectionPage nwp = new NewWizardSelectionPage();
			nwp.selectProject("General","Untitled Text File");
			newWizard.finish();
		}
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
}
