package org.jboss.reddeer.jface.text.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.core.lookup.ShellLookup;

/**
 * This class represents content assistant
 * @author rawagner
 * @since 0.6
 */
public class ContentAssistant {
	
	protected static final Logger log = Logger.getLogger(ContentAssistant.class);
	
	private Table contentAssistTable;
	private Shell contentAssistShell;
	
	/**
	 * Instantiates content assistant with given table of proposals
	 * @param contentAssistTable table which contains proposals
	 */
	public ContentAssistant(Table contentAssistTable){
		this.contentAssistTable = contentAssistTable;
		this.contentAssistShell = ShellLookup.getInstance().getCurrentActiveShell();
	}
	
	/**
	 * Returns content assist proposals
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
	 * Selects given proposal
	 * @param proposal to select
	 */
	public void chooseProposal(String proposal){
		log.debug("Choose content assist proposal - "+proposal);
		contentAssistTable.getItem(proposal).select();
		KeyboardFactory.getKeyboard().invokeKeyCombination(SWT.CR);
	}
	
	/**
	 * Closes content assist shell
	 */
	public void close(){
		log.debug("Closing content assist");
		if(contentAssistShell != null){
			ShellHandler.getInstance().closeShell(contentAssistShell);
		}
	}

}

