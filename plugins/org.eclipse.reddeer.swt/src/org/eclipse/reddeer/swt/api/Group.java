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
