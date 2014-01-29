package org.jboss.reddeer.swt.util;

import org.eclipse.core.runtime.Platform;

/**
 * Provides common utils
 * @author Vlado Pakan
 * @deprecated use ExecutionContext for OS related methods and 
 *
 */
public class Utils {
	private static final String OS_NAME = Platform.getOS().toLowerCase();
	/**
	 * Returns true when machine operating system is os
	 * @param os
	 * @return
	 * @deprecated will be removed with 0.5.0
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
	 * @deprecated will be removed with 0.5.0 
	 */
	public static void checkNotNull(Object argument, String argumentName) {
		if (argument == null) {
			throw new IllegalArgumentException("Argument '" + argumentName 
					+ "' should not be null.");
		}
	}
}
