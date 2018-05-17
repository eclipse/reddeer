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
package org.eclipse.reddeer.swt.impl.toolbar;

import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Default ToolBar implementation.
 * 
 * @author Jiri Peterka
 * @author rhopp
 *
 */
public class DefaultToolBar extends AbstractToolBar {

	/**
	 * Instantiates a new default tool bar.
	 */
	public DefaultToolBar() {
		this(0);
	}
	
	public DefaultToolBar(org.eclipse.swt.widgets.ToolBar widget){
		super(widget);
	}

	/**
	 * Constructor for nth toolbar in currently active shell.
	 * 
	 * @param index
	 *            index of desired shell.
	 */

	public DefaultToolBar(int index) {
		this(new DefaultShell(), index);
	}

	/**
	 * Constructor for nth toolbar within given ReferencedComposite.
	 * 
	 * @param rc
	 *            {@link ReferencedComposite} in which the lookup will be
	 *            performed.
	 *            <p>
	 *            If null, search will be performed in currently active part.
	 * @param index
	 *            index of desired toolbar within given
	 *            {@link ReferencedComposite}.
	 */

	public DefaultToolBar(ReferencedComposite rc, int index) {
		super(rc,index);
	}

}
