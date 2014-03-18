package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * Returns true, if there is server with specified name
 * 
 * @author Vlado Pakan
 *
 */
public class ServerExists implements WaitCondition {

	private String name;
	
	public ServerExists(String name) {
		this.name = name;
	}

	@Override
	public boolean test() {
		try{
			new ServersView().getServer(this.name);
			return true;
		} catch (EclipseLayerException ele){
			return false;
		}		
	}
	
	@Override
	public String description() {
		return "there is server with name: " + this.name;
	}
	
}
