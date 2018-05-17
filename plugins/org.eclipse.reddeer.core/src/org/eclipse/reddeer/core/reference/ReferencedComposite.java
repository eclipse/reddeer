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
package org.eclipse.reddeer.core.reference;

import org.eclipse.swt.widgets.Control;

/**
 * Classes implementing this interface can be used
 * as parent composite widget from which children are located.
 * 
 * @author Jaroslav Jankovic
 * @author Jiri Peterka
 */
public interface ReferencedComposite {
	
	/**
	 * Gets control of referenced composite.
	 * 
	 * @return control
	 */
	Control getControl();
	
}
