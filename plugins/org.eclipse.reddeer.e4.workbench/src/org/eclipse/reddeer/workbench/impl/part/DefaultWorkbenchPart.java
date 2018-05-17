/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.impl.part;

import org.eclipse.reddeer.workbench.core.lookup.WorkbenchPartLookup;

public class DefaultWorkbenchPart extends AbstractWorkbenchPart{
	
	public DefaultWorkbenchPart() {
		super(WorkbenchPartLookup.getInstance().getActiveWorkbenchPart());
	}
	
	public DefaultWorkbenchPart(String label) {
		super(WorkbenchPartLookup.getInstance().getWorkbenchPartWithLabel(label));
	}

}
