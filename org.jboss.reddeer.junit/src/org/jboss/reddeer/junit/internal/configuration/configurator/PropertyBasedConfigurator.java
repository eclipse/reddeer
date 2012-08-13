package org.jboss.reddeer.junit.internal.configuration.configurator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.entity.PropertyBasedConfiguration;
import org.jboss.reddeer.junit.internal.configuration.reader.XMLReader;
import org.jboss.reddeer.junit.internal.configuration.setter.ConfigurationSetter;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Reads property based configuration from XML file and sets the found properties into the requirement.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class PropertyBasedConfigurator implements RequirementConfigurator{

	private static final Logger log = Logger.getLogger(PropertyBasedConfigurator.class);
	
	private XMLReader reader;
	
	private ConfigurationSetter setter;
	
	private Map<Class<? extends Requirement<?>>, PropertyBasedConfiguration> propertyConfigurations;
	
	public PropertyBasedConfigurator(XMLReader reader, ConfigurationSetter setter) {
		super();
		this.reader = reader;
		this.setter = setter;
	}

	@Override
	public void configure(Requirement<?> requirement) {
		if (!(requirement instanceof PropertyConfiguration)){
			throw new IllegalArgumentException("The requirement does not implement " + PropertyConfiguration.class);
		}
		log.debug("Setting property based configuration to requirement " + requirement.getClass());
		PropertyBasedConfiguration config = getPropertyConfigurations().get(requirement.getClass());
		if (config == null){
			throw new RedDeerConfigurationException("The configuration for requirement " + requirement.getClass() + " was not found in the XML file");
		}
		setter.set(requirement, config);
		log.debug("Configuration successfully set");
	}
	
	protected Map<Class<? extends Requirement<?>>, PropertyBasedConfiguration> getPropertyConfigurations(){
		if (propertyConfigurations == null){
			propertyConfigurations = loadPropertyConfigurations();
		}
		return propertyConfigurations;
	}
	
	protected Map<Class<? extends Requirement<?>>, PropertyBasedConfiguration> loadPropertyConfigurations(){
		List<PropertyBasedConfiguration> list = reader.getConfiguration(PropertyBasedConfiguration.class);
		Map<Class<? extends Requirement<?>>, PropertyBasedConfiguration> map = new HashMap<Class<? extends Requirement<?>>, PropertyBasedConfiguration>();
		
		for (PropertyBasedConfiguration config : list){
			if (map.containsKey(config.getRequirementClass())){
				throw new RedDeerConfigurationException("There is more than one configuration in the XML file for requirement class " + config.getRequirementClass());
			}
			map.put(config.getRequirementClass(), config);
		}
		
		return map;
	}
}
