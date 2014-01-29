package org.jboss.reddeer.direct.platform;

import org.eclipse.core.runtime.Platform;

/**
 * Running platforms provides information about currently running instance
 * like Operation System, etc.
 * @author Jiri Peterka
 *
 */
public class RunningPlatform {
	
	private static final String OS_NAME = Platform.getOS().toLowerCase();

	/**
	 * Returns true when machine operating system is os
	 * @param expected os 
	 * @return returns true if given OS matches with running OS
	 */
	public static boolean isOperationSystem(OS os){
		
		boolean result = false;
		
		if (os.equals(OS.WINDOWS)){
			result = OS_NAME.startsWith("win");
		} 
		else if (os.equals(OS.MACOSX)){
			result = OS_NAME.startsWith("macosx");
		}else if (os.equals(OS.LINUX)){
			result = !(OS_NAME.startsWith("macosx") 
					|| OS_NAME.startsWith("win"));
		}
		
		return result;
	}
	
	/**
	 * @return true if running platform is Windows
	 */
	public static boolean isWindows() {
		return OS_NAME.startsWith("win");
	}

	/**
	 * @return true if running platform is OSX
	 */	
	public static boolean isOSX() {
		return OS_NAME.startsWith("macosx");
	}

	/**
	 * @return true if running platform is Linux
	 */		
	public static boolean isLinux() {
		return OS_NAME.startsWith("linux");
	}

}
