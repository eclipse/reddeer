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
