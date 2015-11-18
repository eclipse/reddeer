package org.jboss.reddeer.requirements.server;


/**
 * @author Pavol Srna
 *
 */
public interface IServerReqConfig {
	
	/**
	 * Gets the server family.
	 *
	 * @return the server family
	 */
	public IServerFamily getServerFamily();
	
	/**
	 * Gets the runtime.
	 *
	 * @return the runtime
	 */
	public String getRuntime();

}
