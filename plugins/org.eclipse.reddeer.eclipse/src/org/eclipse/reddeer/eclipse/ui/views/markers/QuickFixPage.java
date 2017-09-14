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
package org.eclipse.reddeer.eclipse.ui.views.markers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;

/**
 * Represents QuickFixPage in QuickFixWizard
 * @author rawagner
 *
 */
public class QuickFixPage extends WizardPage{
	
	public QuickFixPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Get proposed fixes.
	 *
	 * @return proposed fixes
	 */
	public List<String> getAvailableFixes(){
		List<String> toReturn = new ArrayList<String>();
		List<TableItem> items = new DefaultTable(this).getItems();
		for(TableItem i: items){
			toReturn.add(i.getText());
		}
		return toReturn;
	}
	
	/**
	 * Select fix.
	 *
	 * @param fix to select
	 */
	public void selectFix(String fix){
		List<TableItem> items = new DefaultTable(this).getItems();
		for(TableItem i: items){
			if(i.getText().equals(fix)){
				i.select();
				return;
			}
		}
	}

}
