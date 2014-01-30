package org.jboss.reddeer.swt.handler;

import org.eclipse.jface.action.ActionContributionItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

public class ActionContributionItemHandler {
	
	private static ActionContributionItemHandler handler;
	
	private ActionContributionItemHandler(){
		
	}
	
	public static ActionContributionItemHandler getInstance(){
		if(handler == null){
			handler = new ActionContributionItemHandler();
		}
		return handler;
	}
	
	public boolean isEnabled(final ActionContributionItem item){
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return item.isEnabled();
			}
		});
	}
	
	

}
