/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.jface.text.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.shell.AbstractShell;
import org.jboss.reddeer.core.handler.TableItemHandler;
import org.jboss.reddeer.core.lookup.ShellLookup;

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

