package org.jboss.reddeer.junit.requirement;

/**
 * Requirements that want to use custom configuration (not the default property based configuration and XML schema) 
 * need to implement the interface. 
 * 
 * @author Lucia Jelinkova
 *
 * @param <T> Class that will hold the configuration data. 
 */
public interface CustomConfiguration<T> {

	/**
	 * Gets the configuration class.
	 *
	 * @return the configuration class
	 */
	Class<T> getConfigurationClass();
	
	/**
	 * Sets the configuration.
	 *
	 * @param config the new configuration
	 */
	void setConfiguration(T config);
}
