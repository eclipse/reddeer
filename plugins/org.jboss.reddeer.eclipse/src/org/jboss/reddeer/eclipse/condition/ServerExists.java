package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;

/**
 * Returns true, if there is server with specified name
 * 
 * @author Vlado Pakan
 *
 */
public class ServerExists extends AbstractWaitCondition {

	private ServersView view;
	
	private String name;
	
	/**
	 * Construct the condition with a given name.
	 * 
	 * @param name Name
	 */
	public ServerExists(String name) {
		this.name = name;
		view = new ServersView();
		view.open();
	}

	@Override
	public boolean test() {
		try{
			view.getServer(this.name);
			return true;
		} catch (RedDeerException ele){
			return false;
		}		
	}
	
	@Override
	public String description() {
		return "there is server with name: " + this.name;
	}
	
}
