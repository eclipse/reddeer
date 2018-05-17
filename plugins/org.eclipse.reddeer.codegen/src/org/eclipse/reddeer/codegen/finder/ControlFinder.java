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
package org.eclipse.reddeer.codegen.finder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Class for finding controls.
 */
public class ControlFinder extends Finder<Control> {

	@Override
	public List<Control> getChildren(Control child) {
		List<Control> children = new ArrayList<Control>();
		if (child instanceof Composite) {
			Control[] controls = ((Composite) child).getChildren();
			for (Control control : controls) {
				children.add(control);
			}
		}
		return children;
	}
}
