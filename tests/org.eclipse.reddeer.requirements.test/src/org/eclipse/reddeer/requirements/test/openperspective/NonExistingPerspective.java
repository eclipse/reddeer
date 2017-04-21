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
package org.eclipse.reddeer.requirements.test.openperspective;

import org.eclipse.reddeer.eclipse.ui.perspectives.AbstractPerspective;

public class NonExistingPerspective extends AbstractPerspective {

	public NonExistingPerspective() {
		super("NonExistingPerspective");
	}
}
