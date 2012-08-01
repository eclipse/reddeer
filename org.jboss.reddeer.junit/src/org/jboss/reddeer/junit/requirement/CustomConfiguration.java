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

	Class<T> getConfigurationClass();
	
	void setConfiguration(T config);
}
