package org.jboss.reddeer.common.logging;

/**
 * Provides some utility methods useful when creating log messages. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class LoggingUtils {
	/**
	 * Format array of Strings to list of items separated by comma
	 * @param items
	 * @return
	 */
	public static String format(int[] items){
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < items.length; i++){
			sb.append(items[i]);
			if (i != items.length - 1){
				sb.append(", ");
			}
		}

		return sb.toString();
	}
	/**
	 * Format array of Objects to list of items separated by comma
	 * @param items
	 * @return
	 */
	public static String format(Object[] items){
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < items.length; i++){
			sb.append(items[i]);
			if (i != items.length - 1){
				sb.append(", ");
			}
		}

		return sb.toString();
	}
}