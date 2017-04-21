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
