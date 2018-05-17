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
package org.eclipse.reddeer.workbench.impl.toolbar;

import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.eclipse.reddeer.swt.impl.toolbar.AbstractToolBar;
import org.eclipse.reddeer.workbench.core.lookup.ViewToolBarLookup;

/**
 * Workbench toolbar implementation
 * 
 * @author Jiri Peterka
 *
 */
public class ViewToolBar extends AbstractToolBar {

	/**
	 * Constructor for ToolBar of currently active View. If no view is active,
	 * {@link SWTLayerException} is thrown
	 */
	public ViewToolBar() {
		super(ViewToolBarLookup.getInstance().getViewToolBar());
	}

}
