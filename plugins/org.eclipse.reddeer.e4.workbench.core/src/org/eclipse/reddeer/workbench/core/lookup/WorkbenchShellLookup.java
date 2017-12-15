/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.core.lookup;

import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.FrameworkUtil;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IWorkbench;

public class WorkbenchShellLookup {
	
	private static WorkbenchShellLookup instance;
	private IEclipseContext e4Context;
	
	protected static IEclipseContext getEclipseContext() {
		final IEclipseContext serviceContext = EclipseContextFactory.getServiceContext(FrameworkUtil.getBundle(WorkbenchPartLookup.class).getBundleContext());
		return serviceContext.get(IWorkbench.class).getApplication().getContext();
	}

	
	private WorkbenchShellLookup(){
		e4Context = getEclipseContext();
	}
	
	/**
	 * Gets instance of ShellLookup.
	 * 
	 * @return ShellLookup instance
	 */
	public static WorkbenchShellLookup getInstance() {
		if (instance == null)
			instance = new WorkbenchShellLookup();
		return instance;
	}
	
	/**
	 * Gets active workbench shell.
	 * 
	 * @return active workbench shell
	 */
	public Shell getWorkbenchShell() {
		MApplication app = e4Context.get(MApplication.class);
		MWindow window = app.getSelectedElement();
		if(window != null && window.getWidget() instanceof Shell) {
			return (Shell) window.getWidget();
		}
		return null;
	}

}
