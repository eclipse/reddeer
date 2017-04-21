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
package org.eclipse.reddeer.jface.text.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.shell.AbstractShell;
import org.eclipse.reddeer.core.handler.TableItemHandler;
import org.eclipse.reddeer.core.lookup.ShellLookup;

/**
 * This class represents content assistant
 * @author rawagner
 * @since 0.6
 */
public class ContentAssistant extends AbstractShell{
	
	protected static final Logger log = Logger.getLogger(ContentAssistant.class);
	
	private Table contentAssistTable;
	
	/**
	 * Instantiates content assistant with given table of proposals.
	 *
	 * @param contentAssistTable table which contains proposals
	 */
	public ContentAssistant(Table contentAssistTable){
		super(ShellLookup.getInstance().getCurrentActiveShell());
		this.contentAssistTable = contentAssistTable;
	}
	
	/**
	 * Returns content assist proposals.
	 *
	 * @return list of proposals
	 */
	public List<String> getProposals(){
		log.debug("Getting content assist proposals");
		List<String> proposals = new ArrayList<String>();
		log.debug("Content assist proposals:");
		for(TableItem i: contentAssistTable.getItems()){
			String text = i.getText();
			log.debug("  "+text);
			proposals.add(text);
		}
		return proposals;
	} 
	
	/**
	 * Selects given proposal.
	 *
	 * @param proposal to select
	 */
	public void chooseProposal(String proposal){
		log.debug("Choose content assist proposal - "+proposal);
		TableItemHandler.getInstance().setDefaultSelection(contentAssistTable.getItem(proposal).getSWTWidget());
		new WaitWhile(new ShellIsAvailable(this));
	}

}

