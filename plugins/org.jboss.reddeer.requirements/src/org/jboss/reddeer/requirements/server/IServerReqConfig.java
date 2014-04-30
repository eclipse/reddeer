package org.jboss.reddeer.requirements.server;


/**
 * @author Pavol Srna
 *
 */
public interface IServerReqConfig {
	
	public IServerFamily getServerFamily();
	
	public String getRuntime();

}
