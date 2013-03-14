package org.jboss.reddeer.eclipse.ui.project;

/**
 * Utility class to provide appropriate content of plugin files. 
 * 
 * @author sbunciak
 *
 */
public class Templatizer {

	private final String pluginId;
	private final String pluginName;
	private final String pluginVersion;
	private final String pluginProvider;

	public Templatizer(String pluginId, String pluginName,
			String pluginVersion, String pluginProvider) {
		this.pluginId = pluginId;
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
		this.pluginProvider = pluginProvider;
	}

	/**
	 * @param File contents
	 * @return Replaced placeholders with specified values.
	 */
	public String templatize(String contents) {
		return contents.replaceAll("@PLUGIN_NAME@", pluginName)
				.replaceAll("@PLUGIN_ID@", pluginId)
				.replaceAll("@PLUGIN_VERSION@", pluginVersion)
				.replaceAll("@PLUGIN_PROVIDER@", pluginProvider);
	}

}