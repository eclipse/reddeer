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
package org.jboss.reddeer.common.platform;

import org.eclipse.core.runtime.Platform;

/**
 * Running platforms provides information about currently running instance
 * like Operation System, etc.
 * @author Jiri Peterka
 * @since 0.6
 */
public final class RunningPlatform {
	
	private static final String CURRENT_OS = Platform.getOS().toLowerCase();
	
	private RunningPlatform() {};

	/**
	 * Checks if Operating system family belongs to given OS family.
	 *
	 * @param os given os to check with
	 * @return returns true if given os matches with running operation system
	 */
	public static boolean isOperationSystem(OS os){
		
		boolean result = false;
		
		if (os.equals(OS.WINDOWS)){
			result = isWindows();
		} 
		else if (os.equals(OS.MACOSX)){
			result = isOSX();
		}else if (os.equals(OS.LINUX)){
			result = !(isOSX() || isWindows()); 
		}
		
		return result;
	}
	
	/**
	 * Checks if current system belongs To Windows OS family .
	 *
	 * @return true if running platform is Windows
	 */
	public static boolean isWindows() {
		return CURRENT_OS.startsWith("win");
	}

	/**
	 * Checks if current system belongs to Apple OSX family.
	 *
	 * @return true if running platform is OSX
	 */	
	public static boolean isOSX() {
		return CURRENT_OS.startsWith("macosx");
	}

	/**
	 * Checks if current system belongs to GNU/Linux operation system family.
	 *
	 * @return true if running platform is Linux
	 */		
	public static boolean isLinux() {
		return CURRENT_OS.startsWith("linux");
	}

}
