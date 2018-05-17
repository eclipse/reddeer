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
package org.eclipse.reddeer.eclipse.condition;

import java.lang.reflect.Method;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.internal.views.markers.ExtendedMarkersView;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.workbench.impl.view.AbstractView;
import org.eclipse.reddeer.workbench.core.lookup.WorkbenchPartLookup;

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
	 * @param abstractView abstract view
	 * @param viewClass view class
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
