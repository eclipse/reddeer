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
