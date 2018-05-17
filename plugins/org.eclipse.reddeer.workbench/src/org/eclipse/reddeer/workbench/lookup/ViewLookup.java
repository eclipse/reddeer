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
package org.eclipse.reddeer.workbench.lookup;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;

/**
 * View lookup
 * @author rawagner
 *
 */
public class ViewLookup {

	
	private static ViewLookup instance;
	
	/**
	 * Returns instance of ViewLookup
	 * @return ViewLookup instance
	 */
	public static ViewLookup getInstance(){
		if(instance == null){
			instance = new ViewLookup();
		}
		return instance;
	}
	
	/**
	 * Finds registered view path
	 * @param title of view
	 * @return view path
	 */
	public String[] findRegisteredViewPath(Matcher<String> title) {
		IViewDescriptor viewDescriptor = findView(title);
		IViewCategory categoryDescriptor = findViewCategory(viewDescriptor);
		return pathForView(viewDescriptor, categoryDescriptor);
	}

	private IViewDescriptor findView(Matcher<String> title) {
		IViewDescriptor[] views = PlatformUI.getWorkbench().getViewRegistry().getViews();
		for (IViewDescriptor view : views) {
			if (title.matches(view.getLabel())) {
				return view;
			}
		}
		throw new WorkbenchLayerException("View '" + title+ "' is not registered in workbench");
	}

	private IViewCategory findViewCategory(IViewDescriptor viewDescriptor) {
		IViewCategory[] categories = PlatformUI.getWorkbench().getViewRegistry().getCategories();
		for (IViewCategory category : categories) {
			for (IViewDescriptor ivd : category.getViews()) {
				if (ivd.getId().equals(viewDescriptor.getId())) {
					return category;
				}
			}
		}
		throw new WorkbenchLayerException("View '" + viewDescriptor.getLabel()+ "' is not registered in any category");
	}

	private String[] pathForView(IViewDescriptor viewDescriptor, IViewCategory categoryDescriptor) {
		String[] path = new String[2];
		path[0] = categoryDescriptor.getLabel();
		path[1] = viewDescriptor.getLabel();
		return path;
	}
}