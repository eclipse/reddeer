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
