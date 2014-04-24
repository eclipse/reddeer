package org.jboss.reddeer.eclipse.jface.text.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.handler.ShellHandler;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.swt.lookup.ShellLookup;

/**
 * This class represents content assistant
 * @author rawagner
 *
 */
public class ContentAssistant {
	
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
		List<String> proposals = new ArrayList<String>();
		for(TableItem i: contentAssistTable.getItems()){
			proposals.add(i.getText());
		}
		return proposals;
	}
	
	/**
	 * Selects given proposal
	 * @param proposal to select
	 */
	public void chooseProposal(String proposal){
		contentAssistTable.getItem(proposal).select();
		KeyboardFactory.getKeyboard().invokeKeyCombination(SWT.CR);
	}
	
	/**
	 * Closes content assist shell
	 */
	public void close(){
		if(contentAssistShell != null){
			ShellHandler.getInstance().closeShell(contentAssistShell);
		}
	}

}

