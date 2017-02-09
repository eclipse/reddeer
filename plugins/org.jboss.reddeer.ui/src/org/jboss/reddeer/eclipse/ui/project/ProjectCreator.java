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
package org.jboss.reddeer.eclipse.ui.project;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * Helper class to generate RedDeer project default content.
 * 
 * @author sbunciak
 *
 */
public class ProjectCreator {

	private final String pluginId;
	private final String pluginProvider;
	private final String pluginVersion;
	private final String pluginName;
	private final boolean generateTest; 
	private final IWorkspaceRoot root;

	/**
	 * Creates instance of Project template creator.
	 * 
	 * @param pluginId to be used
	 * @param pluginName to be used
	 * @param pluginVersion to be used
	 * @param pluginProvider to be used
	 * @param generateTest whether should generate test or not
	 * @param root workspace root
	 */
	public ProjectCreator(String pluginId, String pluginName,
			String pluginVersion, String pluginProvider, boolean generateTest, IWorkspaceRoot root) {
		this.pluginId = pluginId;
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
		this.pluginProvider = pluginProvider;
		this.root = root;
		this.generateTest = generateTest;
	}

	/**
	 * Create default project structure in workspace after wizard end.
	 * 
	 * @throws CoreException if something goes wrong
	 */
	public void create() throws CoreException {
		IProject project = getProject();
		project.create(null);
		project.open(null);

		project.getFile("build.properties").create(stream("_build.properties"),
				true, null);
		project.getFile(".classpath").create(stream("_classpath"), true, null);
		IFolder folder = project.getFolder("META-INF");
		folder.create(true, true, null);

		folder.getFile("MANIFEST.MF")
				.create(stream("_MANIFEST.MF"), true, null);

		project.getFile(".project").setContents(stream("_project"), true,
				false, null);

		IFolder src = project.getFolder("src");
		src.create(true, true, null);
		
		if (generateTest) {
			IFolder org = src.getFolder("org");
			org.create(true, true, null);
			IFolder reddeer = org.getFolder("reddeer");
			reddeer.create(true, true, null);
			IFolder test = reddeer.getFolder("test");
			test.create(true, true, null);
			test.getFile("RedDeerTest.java").create(stream("_REDDEER_TEST_JAVA"), true, null);
			
			project.getFile("pluginCustomization.ini").create(stream("pluginCustomization.ini"), true, null);
			project.getFile("RedDeerTest.launch").create(stream("RedDeerTest.launch"), true, null);			
		}		

	}

	/**
	 * Delete project from workspace
	 */
	public void delete() {
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(pluginId);
		try {
			project.delete(true, null);
		} catch (CoreException e) {
			throw new RuntimeException("Could not delete project "
					+ project.getName(), e);
		}
	}

	private InputStream stream(String path) {
		InputStream resourceAsStream = getClass().getClassLoader()
				.getResourceAsStream(
						"org/jboss/reddeer/eclipse/ui/project/" + path);
		String contents = new Templatizer(pluginId, pluginName, pluginVersion,
				pluginProvider).templatize(read(resourceAsStream));
		return new ByteArrayInputStream(contents.getBytes());
	}

	private String read(InputStream is) {
		StringBuffer buffer = new StringBuffer();
		InputStreamReader in = null;
		try {
			in = new InputStreamReader(is, "utf-8");
			while (in.ready()) {
				buffer.append((char) in.read());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(in);
		}
		return buffer.toString();
	}

	private void close(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private IProject getProject() {
		return root().getProject(pluginId);
	}

	private IWorkspaceRoot root() {
		return root;
	}
	
	public void generateTest() {
		
	}

}