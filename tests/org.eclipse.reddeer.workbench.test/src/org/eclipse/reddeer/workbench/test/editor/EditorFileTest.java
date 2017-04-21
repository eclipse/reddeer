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
package org.eclipse.reddeer.workbench.test.editor;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditorFile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
@CleanWorkspace
@RunWith(RedDeerSuite.class)
public class EditorFileTest {

	private static final String PROJECT_NAME = "MyProject";
	private static final String FILE_NAME = "people.xml";

	private static IFile iFile;

	@BeforeClass
	public static void preparePropject() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject project = root.getProject(PROJECT_NAME);
		IFile file = project.getFile(FILE_NAME);
		// at this point, no resources have been created
		if (!project.exists())
			project.create(null);
		if (!project.isOpen())
			project.open(null);
		if (!file.exists()) {
			String content = "<people><person><name>apodhrad</name><company>RedHat</company></person></people>";
			InputStream source = new ByteArrayInputStream(content.getBytes());
			file.create(source, IResource.NONE, null);
		}
		iFile = file;
	}

	@AfterClass
	public static void cleanWorkspace() {
		new CleanWorkspaceRequirement().fulfill();
	}

	@Test
	public void testGettingRelativePath() {
		DefaultEditorFile editorFile = new DefaultEditorFile(iFile);
		assertEquals("/" + PROJECT_NAME + "/" + FILE_NAME, editorFile.getRelativePath());
	}

	@Test
	public void testGettingAbsolutePath() {
		DefaultEditorFile editorFile = new DefaultEditorFile(iFile);
		String rootPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		assertEquals(rootPath + "/" + PROJECT_NAME + "/" + FILE_NAME, editorFile.getAbsolutePath());
	}

}
