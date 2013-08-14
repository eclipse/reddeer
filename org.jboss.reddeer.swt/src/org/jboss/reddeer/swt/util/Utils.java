package org.jboss.reddeer.swt.util;

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
}
