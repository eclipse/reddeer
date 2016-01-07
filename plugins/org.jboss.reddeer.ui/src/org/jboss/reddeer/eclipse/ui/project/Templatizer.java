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

	/**
	 * Creates instance of RedDeer project Templatizer.
	 * 
	 * @param pluginId to be used in project files
	 * @param pluginName to be used in project files
	 * @param pluginVersion to be used in project files
	 * @param pluginProvider to be used in project files
	 */
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