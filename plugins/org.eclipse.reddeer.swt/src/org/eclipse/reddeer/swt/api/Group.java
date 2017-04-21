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
package org.eclipse.reddeer.swt.api;

import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * API for group manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Group extends ReferencedComposite, Control<org.eclipse.swt.widgets.Group> {
	
	/**
	 * Gets text of the group.
	 * 
	 * @since 0.4
	 * @return text of the group
	 */
	String getText();
}
