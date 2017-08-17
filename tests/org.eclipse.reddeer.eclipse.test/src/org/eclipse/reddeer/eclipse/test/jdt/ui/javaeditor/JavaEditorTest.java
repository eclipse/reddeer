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
package org.eclipse.reddeer.eclipse.test.jdt.ui.javaeditor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.debug.ui.views.breakpoints.BreakpointsView;
import org.eclipse.reddeer.eclipse.jdt.ui.javaeditor.JavaEditor;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassCreationWizard;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for Java editor.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
@CleanWorkspace
@RunWith(RedDeerSuite.class)
public class JavaEditorTest {

	public static final String PROJECT_NAME = "java_project";
	public static final String CLASS_NAME = "Demo";

	@BeforeClass
	public static void prepareJavaProject() {
		JavaProjectWizard javaProjectWizard = new JavaProjectWizard();
		javaProjectWizard.open();
		new NewJavaProjectWizardPageOne(javaProjectWizard).setProjectName(PROJECT_NAME);
		javaProjectWizard.finish();

		NewClassCreationWizard classCreationWizard = new NewClassCreationWizard();
		classCreationWizard.open();
		new NewClassWizardPage(classCreationWizard).setName(CLASS_NAME);
		new NewClassWizardPage(classCreationWizard).setStaticMainMethod(true);
		classCreationWizard.finish();

		JavaEditor javaEditor = new JavaEditor(CLASS_NAME + ".java");
		javaEditor.insertLine(6, "\t\tSystem.out.println(\"Hello World\");");
		javaEditor.save();
	}

	@AfterClass
	public static void cleanWorkspace() {
		new CleanWorkspaceRequirement().fulfill();
	}

	@Before
	public void removeAllBreakpoints() {
		new BreakpointsView().open();
		new BreakpointsView().removeAllBreakpoints();
	}

	@Test
	public void testAddingClassBreakpoint() {
		JavaEditor javaEditor = new JavaEditor(CLASS_NAME + ".java");
		javaEditor.addBreakpoint(javaEditor.getLineOfText("public class Demo"));

		assertBreakpoint("Demo", "public class Demo");
	}

	@Test
	public void testAddingMethodBreakpoint() {
		JavaEditor javaEditor = new JavaEditor(CLASS_NAME + ".java");
		javaEditor.addBreakpoint(javaEditor.getLineOfText("public static void main"));

		assertBreakpoint("Demo [entry] - main(String[])", "public static void main");
	}

	@Test
	public void testAddingLineBreakpoint() {
		JavaEditor javaEditor = new JavaEditor(CLASS_NAME + ".java");
		javaEditor.addBreakpoint(javaEditor.getLineOfText("System.out.println"));

		assertBreakpoint("Demo [line: 7] - main(String[])", "System.out.println");
	}

	@Test
	public void testRemovingBreakpoint() {
		JavaEditor javaEditor = new JavaEditor(CLASS_NAME + ".java");
		javaEditor.addBreakpoint(javaEditor.getLineOfText("public class Demo"));
		javaEditor.addBreakpoint(javaEditor.getLineOfText("public static void main"));
		javaEditor.addBreakpoint(javaEditor.getLineOfText("System.out.println"));

		assertEquals(3, javaEditor.getBreakpoints().size());
		javaEditor.removeBreakpoint(javaEditor.getLineOfText("public static void main"));
		assertEquals(2, javaEditor.getBreakpoints().size());
		Assert.assertNull(javaEditor.getBreakpoint(javaEditor.getLineOfText("public static void main")));
	}

	private static void assertBreakpoint(String label, String text) {
		JavaEditor javaEditor = new JavaEditor(CLASS_NAME + ".java");
		try {
			assertEquals(label, javaEditor.getBreakpoint(javaEditor.getLineOfText(text)));
		} catch (AssertionError e) {
			e.printStackTrace();
		}
		assertEquals(1, javaEditor.getBreakpoints().size());

		new BreakpointsView().open();
		assertTrue(new BreakpointsView().isBreakpointAvailable(label));
	}

}
