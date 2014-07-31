package org.jboss.reddeer.direct.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

/**
 * This class provides core support for Eclipse preferences.
 * 
 * @author apodhrad
 * 
 */
public class Preferences {

	/**
	 * 
	 * Returns value of plugin/key, if it is not set it will return the default
	 * value. If it doesn't exist it will return null.
	 * 
	 * @param plugin
	 *            plugin name
	 * @param key
	 *            key
	 * @return value of plugin/key or default value if it is not set or null if
	 *         it doesn't exist
	 */
	public static String get(String plugin, String key) {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(plugin);
		return prefs.get(key, getDefault(plugin, key));
	}

	/**
	 * Returns default value of plugin/key, if it doesn't exist it will return
	 * null.
	 * 
	 * @param plugin
	 *            plugin name
	 * @param key
	 *            key
	 * @return default value of plugin/key or null if it doesn't exist
	 */
	public static String getDefault(String plugin, String key) {
		IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(plugin);
		return prefs.get(key, null);
	}

	/**
	 * Sets a given value to plugin/key preferences.
	 * 
	 * @param plugin
	 *            plugin name
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public static void set(String plugin, String key, String value) {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(plugin);
		prefs.put(key, value);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			throw new RuntimeException("Cannot store preferences for " + plugin + "/" + key + "='" + value + "'", e);
		}
	}

	/**
	 * Sets the default value for a given plugin/key.
	 * 
	 * @param plugin
	 *            plugin name
	 * @param key
	 *            key
	 */
	public static void setDefault(String plugin, String key) {
		set(plugin, key, getDefault(plugin, key));
	}

	/**
	 * Sets the default value for all keys in a given plugin.
	 * 
	 * @param plugin
	 *            plugin name
	 */
	public static void setDefault(String plugin) {
		String[] key = getKeys(plugin);
		for (int i = 0; i < key.length; i++) {
			setDefault(plugin, key[i]);
		}
	}

	/**
	 * Returns all keys of a given plugin.
	 * 
	 * These keys are taken from the DefaultScope.
	 * 
	 * @param plugin
	 *            plugin name
	 * @return keys
	 */
	public static String[] getKeys(String plugin) {
		IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(plugin);
		try {
			return prefs.keys();
		} catch (BackingStoreException e) {
			throw new RuntimeException("Cannot get keys for plugin '" + plugin + "'", e);
		}
	}

}
