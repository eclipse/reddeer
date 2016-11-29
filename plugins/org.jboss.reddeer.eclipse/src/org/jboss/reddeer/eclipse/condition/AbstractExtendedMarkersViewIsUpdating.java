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
package org.jboss.reddeer.eclipse.condition;

import java.lang.reflect.Method;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.internal.views.markers.ExtendedMarkersView;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.workbench.impl.view.AbstractView;
import org.jboss.reddeer.workbench.core.lookup.WorkbenchPartLookup;

/**
 * Returns true if marker based view is updating its UI. This is an abstract class and 
 * its subclasses should specify the concrete view. 
 * 
 * @author Jiri Peterka
 * 
 */
@SuppressWarnings("restriction")
public abstract class AbstractExtendedMarkersViewIsUpdating extends AbstractWaitCondition {

	private ExtendedMarkersView markersView;

	/**
	 * Construct the condition.
	 * @param abstractView 
	 * @param class1 
	 */
	public AbstractExtendedMarkersViewIsUpdating(AbstractView abstractView, final Class<? extends ExtendedMarkersView> viewClass) {
		abstractView.open();
		for (IViewPart part : WorkbenchPartLookup.getInstance().getOpenViews()){
			if (part.getClass().equals(viewClass)){
				markersView = (ExtendedMarkersView) part;
				return;
			}
		}
		throw new EclipseLayerException("Cannot find view with the specified class " + viewClass);				
	}

	@Override
	public boolean test() {
		boolean res = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				boolean result = false;
				try {
					Method m = ExtendedMarkersView.class.getDeclaredMethod(
							"isUIUpdating", new Class<?>[0]);
					m.setAccessible(true);
					result = (Boolean) m.invoke(markersView, new Object[0]);
				} catch (Exception e) {
					throw new EclipseLayerException("Cannot invoke isUIUpdating", e);
				}
				return result;
			}
		});
		return res;
	}

	@Override
	public String description() {
		return "marker view is updating";
	}
}
