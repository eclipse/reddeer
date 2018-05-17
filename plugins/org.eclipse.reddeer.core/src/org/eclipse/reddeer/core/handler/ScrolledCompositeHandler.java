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
package org.eclipse.reddeer.core.handler;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link ScrolledComposite}
 * widgets
 * 
 * @author rawagner
 *
 */
public class ScrolledCompositeHandler extends ControlHandler {

	private static ScrolledCompositeHandler instance;

	/**
	 * Gets instance of ScrolledCompositeHandler.
	 * 
	 * @return instance of ScrolledCompositeHandler
	 */
	public static ScrolledCompositeHandler getInstance() {
		if (instance == null) {
			instance = new ScrolledCompositeHandler();
		}
		return instance;
	}

	/**
	 * Gets content that is being scrolled
	 * 
	 * @param scrolledComposite
	 *            composite to handle
	 * @return content that is being scrolled
	 */
	public Control getContent(final ScrolledComposite scrolledComposite) {
		return Display.syncExec(new ResultRunnable<Control>() {

			@Override
			public Control run() {
				return scrolledComposite.getContent();
			}
		});
	}

}
