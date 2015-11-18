package org.jboss.reddeer.junit.internal.configuration.configurator;

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.reader.XMLReader;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Reads custom configuration from XML file and sets the configuration into the requirement. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class CustomConfigurator implements RequirementConfigurator{

	private static final Logger log = Logger.getLogger(CustomConfigurator.class);
	
	private XMLReader reader;
	
	/**
	 * Instantiates a new custom configurator.
	 *
	 * @param reader the reader
	 */
	public CustomConfigurator(XMLReader reader) {
		super();
		this.reader = reader;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.internal.configuration.configurator.RequirementConfigurator#configure(org.jboss.reddeer.junit.requirement.Requirement)
	 */
	@Override
	public void configure(Requirement<?> requirement) {
		
		if (!(requirement instanceof CustomConfiguration<?>)){
			throw new IllegalArgumentException("The requirement does not implement " + CustomConfiguration.class);
		}
		
		log.debug("Setting custom configuration to requirement " + requirement.getClass());
		
		@SuppressWarnings("unchecked")
		CustomConfiguration<Object> customConfiguration = (CustomConfiguration<Object>) requirement;
		
		log.debug("Configuration object associated with requirement " + requirement.getClass() + " is " + customConfiguration.getConfigurationClass());
		
		List<Object> configs = reader.getConfiguration(customConfiguration.getConfigurationClass());
		if (configs.isEmpty()){
			throw new RedDeerConfigurationException("No configuration found in XML configuration file for requirement class " + customConfiguration.getClass());
		}
		
		if (configs.size() > 1){
			throw new RedDeerConfigurationException("More then one configuration found in XML configuration file for requirement class " + customConfiguration.getClass());
		}
		
		customConfiguration.setConfiguration(configs.get(0));
		log.debug("Configuration successfully set");
	}
}
