package org.jboss.reddeer.swt.util;

import java.util.regex.Pattern;

import org.eclipse.core.runtime.Platform;

/**
 * Provides common utils
 * @author Vlado Pakan
 *
 */
public class Utils {
	private static final String OS_NAME = Platform.getOS().toLowerCase();
	/**
	 * Returns true when machine operating system is os
	 * @param os
	 * @return
	 */
	public static boolean isRunningOS (OS os){
		
		boolean result = false;
		
		if (os.equals(OS.WINDOWS)){
			result = Utils.OS_NAME.startsWith("win");
		} 
		else if (os.equals(OS.MACOSX)){
			result = Utils.OS_NAME.startsWith("macosx");
		}else if (os.equals(OS.LINUX)){
			result = !(Utils.OS_NAME.startsWith("macosx") 
					|| Utils.OS_NAME.startsWith("win"));
		}
		
		return result;
	}
	
	/**
	 * Simple validation method for non-nullable arguments.
	 * @param argument to check
	 * @param argumentName for exception message
	 * @throws IllegalArgumentException if {@code argument} is {@code null}
	 */
	public static void checkNotNull(Object argument, String argumentName) {
		if (argument == null) {
			throw new IllegalArgumentException("Argument '" + argumentName 
					+ "' should not be null.");
		}
	}

	/**
	 * If length of string is lower or equal than specified, method returns original string.
	 * Otherwise, substring of corresponding length with postfix "<... shortened>" is returned.
	 *
	 * @param s String to shorten
	 * @param maxLength Length string is not supposed to exceed.
	 * @return
	 */
	public static String shorten(String s, int maxLength) {
		return (s.length() <= maxLength ? s : s.substring(0, maxLength) + "<... shortened>");
	}

	/**
	 * Join items into one string, placing delimiter between each two items.
	 *
	 * @param items
	 * @param delimiter
	 * @return
	 */
	public static String join(String[] items, String delimiter) {
		if (items == null || items.length == 0 || delimiter == null)
			return "";

		StringBuffer result = new StringBuffer();

		for (String item: items) {
			result.append(item);
			result.append(delimiter);
		}

		result.lastIndexOf(delimiter);
		result.replace(result.length() - delimiter.length(), result.length(), "");

		return result.toString();
	}

	/**
	 * Join items into one string, placing delimiter between each two items.
	 *
	 * @param items Pattern objects, their toString() values are used in join operation
	 * @param delimiter
	 * @return
	 */
	public static String join(Pattern[] items, String delimiter) {
		String sitems[] = new String[items.length];

		for(int i = 0; i < items.length; i++)
			sitems[i] = items[i].toString();

		return join(sitems, delimiter);
	}
}
