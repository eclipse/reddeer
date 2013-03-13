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

public class ProjectCreator {

	private final String pluginId;
	private final String pluginProvider;
	private final String pluginVersion;
	private final String pluginName;
	private final IWorkspaceRoot root;

	public ProjectCreator(String pluginId, String pluginName,
			String pluginVersion, String pluginProvider, IWorkspaceRoot root) {
		this.pluginId = pluginId;
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
		this.pluginProvider = pluginProvider;
		this.root = root;
	}

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

		project.getFolder("src").create(true, true, null);
	}

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

}