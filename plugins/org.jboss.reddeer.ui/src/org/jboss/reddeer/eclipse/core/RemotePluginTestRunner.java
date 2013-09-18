package org.jboss.reddeer.eclipse.core;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.internal.junit.runner.RemoteTestRunner;
import org.osgi.framework.Bundle;

/**
 * Copied verbatim from PDE to remove PDE dependency. Runs JUnit tests contained
 * inside a plugin.
 */
@SuppressWarnings("all")
public class RemotePluginTestRunner extends RemoteTestRunner {

	private String fTestPluginName;
	private ClassLoader fLoaderClassLoader;

	class BundleClassLoader extends ClassLoader {
		private Bundle bundle;

		public BundleClassLoader(Bundle target) {
			this.bundle = target;
		}

		protected Class findClass(String name) throws ClassNotFoundException {
			return bundle.loadClass(name);
		}

		protected URL findResource(String name) {
			return bundle.getResource(name);
		}

		protected Enumeration findResources(String name) throws IOException {
			return bundle.getResources(name);
		}
	}

	/**
	 * The main entry point. Supported arguments in addition to the ones
	 * supported by RemoteTestRunner:
	 * 
	 * <pre>
	 * -testpluginname: the name of the plugin containing the tests.
	 * </pre>
	 * 
	 * @see RemoteTestRunner
	 */

	public static void main(String[] args) {
		RemotePluginTestRunner testRunner = new RemotePluginTestRunner();
		testRunner.init(args);
		testRunner.run();
	}

	/**
	 * Returns the Plugin class loader of the plugin containing the test.
	 * 
	 * @see RemoteTestRunner#getTestClassLoader()
	 */
	protected ClassLoader getTestClassLoader() {
		final String pluginId = fTestPluginName;
		return getClassLoader(pluginId);
	}

	public ClassLoader getClassLoader(final String pluginId) {
		Bundle bundle = Platform.getBundle(pluginId);
		if (bundle == null)
			throw new IllegalArgumentException(
					"No Classloader found for plug-in " + pluginId); //$NON-NLS-1$
		return new BundleClassLoader(bundle);
	}

	public void init(String[] args) {
		readPluginArgs(args);
		defaultInit(args);
	}

	public void readPluginArgs(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (isFlag(args, i, "-testpluginname")) //$NON-NLS-1$
				fTestPluginName = args[i + 1];

			if (isFlag(args, i, "-loaderpluginname")) //$NON-NLS-1$
				fLoaderClassLoader = getClassLoader(args[i + 1]);
		}

		if (fTestPluginName == null)
			throw new IllegalArgumentException(
					"Parameter -testpluginnname not specified."); //$NON-NLS-1$

		if (fLoaderClassLoader == null)
			fLoaderClassLoader = getClass().getClassLoader();
	}

	protected Class loadTestLoaderClass(String className)
			throws ClassNotFoundException {
		return fLoaderClassLoader.loadClass(className);
	}

	private boolean isFlag(String[] args, int i, final String wantedFlag) {
		String lowerCase = args[i].toLowerCase(Locale.ENGLISH);
		return lowerCase.equals(wantedFlag) && i < args.length - 1;
	}
}