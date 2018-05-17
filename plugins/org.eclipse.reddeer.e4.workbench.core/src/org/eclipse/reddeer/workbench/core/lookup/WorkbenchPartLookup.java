/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.core.lookup;

import org.osgi.framework.FrameworkUtil;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Control;

/**
 * Workbench part lookup contains methods for looking up specific workbench part.
 * 
 * @author rawagner
 *
 */
public class WorkbenchPartLookup {

	private static WorkbenchPartLookup instance;
	private IEclipseContext e4Context;
	
	protected static IEclipseContext getEclipseContext() {
		final IEclipseContext serviceContext = EclipseContextFactory.getServiceContext(FrameworkUtil.getBundle(WorkbenchPartLookup.class).getBundleContext());
		return serviceContext.get(IWorkbench.class).getApplication().getContext();
	}

	private WorkbenchPartLookup() {
		e4Context = getEclipseContext();
	}

	/**
	 * Gets instance of WorkbenchPartLookup.
	 * 
	 * @return WorkbenchPartLookup instance
	 */
	public static WorkbenchPartLookup getInstance() {
		if (instance == null) {
			instance = new WorkbenchPartLookup();
		}
		return instance;
	}

	/**
	 * Gets active workbench part.
	 * 
	 * @return active workbench part
	 */
	public MPart getActiveWorkbenchPart() {
		EPartService partService = e4Context.get(EPartService.class);
		return partService.getActivePart();
	}
	
	public MPart getWorkbenchPartWithLabel(String label) {
		EPartService partService = e4Context.get(EPartService.class);
		for(MPart part: partService.getParts()) {
			if(label.equals(part.getLabel())) {
				return part;
			}
		}
		return null;
	}
	
	public Control getActiveWorkbenchPartControl(){
		return getWorkbenchControl(getActiveWorkbenchPart());
	}
	
	public String getActiveWorkbenchPartTitle() {
		MPart activePart = getActiveWorkbenchPart();
		if(activePart != null) {
			return activePart.getLabel();
		}
		return null;
	}
	
	public Control getWorkbenchControl(MPart workbenchPart) {
		if(workbenchPart != null && workbenchPart.getWidget() instanceof Control) {
			return (Control)workbenchPart.getWidget();
		}
		return null;
	}
}
