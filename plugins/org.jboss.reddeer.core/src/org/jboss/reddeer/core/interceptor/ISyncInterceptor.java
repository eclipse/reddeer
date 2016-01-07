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
package org.jboss.reddeer.core.interceptor;

/**
 * Display SyncExec interceptor. Serves to intercept user operation before and after each
 * call of Display.syncExec.
 *  
 * @author Jiri Peterka
 *
 */
public interface ISyncInterceptor {

	/**
	 * Method to be executed before syncExec is performed.
	 */
	void beforeSyncOp();
	
	/**
	 * Method to be executed after syncExec is performed.
	 */
	void afterSyncOp();
}
