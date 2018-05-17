/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.logparser;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class LogParserLog {
	public static void logInfo (String message){
		log(IStatus.INFO , IStatus.OK , message, null);
	}
	
	public static void logError (String message , Throwable exception){
		log(IStatus.ERROR , IStatus.OK , message, exception);
	}
	
	public static void logError (Throwable exception){
		logError("Unexpected error", exception);
	}
	
	public static void log (int severity , int code, String message, Throwable exception){
		log (createStatus(severity , code, message, exception));
	}
	
	public static void log (IStatus status){
		LogParserActivator.getDefault().getLog().log(status);
	}
	
	public static IStatus createStatus (int severity , int code, String message, Throwable exception){
		return new Status(severity, LogParserActivator.PLUGIN_ID, code , message , exception);
	}

}
