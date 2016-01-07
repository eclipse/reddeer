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
package org.jboss.reddeer.eclipse.test.wst.server.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.server.core.model.RuntimeDelegate;

public class TestServerRuntime extends RuntimeDelegate {
	
	public static final String ID = "org.jboss.reddeer.eclipse.test.wst.server.testserverruntime";
	
	public static final String CATEGORY = "Basic";
	
	public static final String NAME = "Test runtime";
	
	public static final String TYPE = "Test runtime";
	
	public IStatus validate() {
		return Status.OK_STATUS;
	}
	
	public void setDefaults(IProgressMonitor monitor) {
		getRuntimeWorkingCopy().setLocation(new Path(""));
	}
}