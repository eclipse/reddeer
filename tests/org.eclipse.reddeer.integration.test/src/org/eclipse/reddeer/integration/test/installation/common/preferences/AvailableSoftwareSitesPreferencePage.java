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
package org.eclipse.reddeer.integration.test.installation.common.preferences;

import java.util.List;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;

/**
 * Represents a Install/Update -> Available Update Sites preference page.
 * 
 * @author Ondrej Dockal
 */
public class AvailableSoftwareSitesPreferencePage extends PreferencePage {

	public AvailableSoftwareSitesPreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, new String[] {"Install/Update", "Available Software Sites"});
	}
	
	public List<TableItem> getItems() {
		return new DefaultTable().getItems();
	}
	
	public void toggleAllItems(boolean toggle) {
		for(TableItem item: getItems()) {
			item.setChecked(toggle);
		}
	}
	
}
