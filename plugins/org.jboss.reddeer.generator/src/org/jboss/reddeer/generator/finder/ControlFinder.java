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
package org.jboss.reddeer.generator.finder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Class for finding controls.
 * 
 * @author apodhrad
 *
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
