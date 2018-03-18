/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.wst.server.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.server.core.model.RuntimeDelegate;

public class TestServerRuntime2 extends RuntimeDelegate {
	
	public static final String ID = "org.eclipse.reddeer.eclipse.test.wst.server.testserverruntime";
	
	public static final String CATEGORY = "Basic";
	
	public static final String NAME = "Test runtime 2";
	
	public static final String TYPE = "Test runtime 2";
	
	public IStatus validate() {
		return Status.OK_STATUS;
	}
	
	public void setDefaults(IProgressMonitor monitor) {
		getRuntimeWorkingCopy().setLocation(new Path(""));
	}
}