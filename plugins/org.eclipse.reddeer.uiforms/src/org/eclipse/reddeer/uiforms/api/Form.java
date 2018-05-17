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
package org.eclipse.reddeer.uiforms.api;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.Control;

/**
 * Represents Eclipse Form. This class will be used mainly for its children discovering
 * 
 * @author Lucia Jelinkova
 *
 */
public interface Form extends ReferencedComposite, Control<org.eclipse.ui.forms.widgets.Form> {

	/**
	 * Returns form's title. 
	 *
	 * @return the text
	 */
	String getText();
}
