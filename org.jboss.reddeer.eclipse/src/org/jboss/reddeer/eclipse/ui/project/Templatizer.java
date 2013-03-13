package org.jboss.reddeer.eclipse.ui.project;

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

	public String templatize(String contents) {
		return contents.replaceAll("@PLUGIN_NAME@", pluginName)
				.replaceAll("@PLUGIN_ID@", pluginId)
				.replaceAll("@PLUGIN_VERSION@", pluginVersion)
				.replaceAll("@PLUGIN_PROVIDER@", pluginProvider);
	}

}