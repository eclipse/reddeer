package org.jboss.reddeer.junit.internal.configuration;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.internal.configuration.entity.PropertyBasedConfiguration;
import org.jboss.reddeer.junit.internal.configuration.reader.XMLReader;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.requirement.RequirementException;

/**
 * Represents requirement configuration class and its configuration object defined in xml file.
 * @author odockal
 *
 */
public class RequirementConfiguration {
	
	private List<Object> configurations = new ArrayList<>();
	
	private Class<?> requirementConfigurationClass;
	
	/**
	 * Instantiates requirement configuration object that consists of 
	 * given requirement configuration class, xml reader as provider to configuration file and
	 * gives access to all configuration based on given configuration class.
	 * @param requirementConfigClass configuration class of the requirement
	 * @param reader the reader
	 */
	public RequirementConfiguration(Class<?> requirementConfigClass, XMLReader reader) {
		this.requirementConfigurationClass = requirementConfigClass;
		readConfigurationFromXML(reader);
	}
	
	/**
	 * Returns list of configurations.
	 * @return list of configuration objects
	 */
	public List<Object> getConfiguration() {
		if (this.configurations.isEmpty()) {
			throw new RedDeerConfigurationException("No configuration found in XML configuration file for requirement class " + this.requirementConfigurationClass);
		}
		return this.configurations;
	}
	
	/**
	 * Requirement configuration class.
	 * @return requirement configuration class
	 */
	public Class<?> getRequirementConfiguration() {
		return this.requirementConfigurationClass;
	}
	
	/**
	 * Based on given xml reader and instance's {@link requirementConfigurationClass} object
	 * reads all applicable configuration from xml file that corresponds to configuration class and
	 * insert them into instance's list of {@link configurations}. 
	 * @param reader the reader
	 */
	private void readConfigurationFromXML(XMLReader reader) {
		
		@SuppressWarnings("unchecked")
		Requirement<Annotation> requirement = (Requirement<Annotation>) createInstance(this.requirementConfigurationClass.getEnclosingClass());
		
		if (!(requirement instanceof CustomConfiguration || requirement instanceof PropertyConfiguration)){
			throw new IllegalArgumentException("The requirement does not implement neither of " +
												CustomConfiguration.class + " or " + PropertyConfiguration.class);
		}
		
		List<Object> configurationList = new ArrayList<>();
		
		if (requirement instanceof CustomConfiguration) {
		
			// maybe use newInstance on annotation object (CustomConfiguration class)
			@SuppressWarnings("unchecked")
			CustomConfiguration<Object> customConfig = (CustomConfiguration<Object>) requirement;
			
			configurationList = reader.getConfiguration(customConfig.getConfigurationClass());
		} else if (requirement instanceof PropertyConfiguration) {
			
			configurationList = loadPropertyConfigurations(reader);
		}
		this.configurations = configurationList;
	}
	
	/**
	 * Load property based configurations.
	 * @param reader the xml reader
	 * @return map containing property based configuration
	 */
	private List<Object> loadPropertyConfigurations(XMLReader reader) {
		List<PropertyBasedConfiguration> list = reader.getConfiguration(PropertyBasedConfiguration.class);
		List<Object> configurationList = new ArrayList<>();
		
		for (PropertyBasedConfiguration config : list) {
			if (this.requirementConfigurationClass.getEnclosingClass().getCanonicalName().equalsIgnoreCase(config.getRequirementClassName())) {
				configurationList.add(config);
			}
		}
		return configurationList;
	}
	
	/**
	 * Iterate over all configurations and checks if any is applicable.
	 * @param annotations all requirement annotations used
	 */
	public void checkRequirementConfiguration(List<Annotation> annotations) {
		Iterator<Object> iterator = getConfiguration().iterator();
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			if (!configurationFitsRequirement(obj, annotations)) {
				iterator.remove();
			}
		}
	}
	
	/**
	 * Tests whether given configuration is acceptable for any of given annotations.
	 * @param configuration configuration instance or object, particular configuration values
	 * @param annotations list of annotations
	 * @return true, if configuration object fits requirement
	 */
	private boolean configurationFitsRequirement(Object configuration, List<Annotation> annotations) {
		for (Annotation annotation : annotations) {
			// test whether requirement is of particular configuration class
			if (annotation.annotationType().getEnclosingClass() != this.requirementConfigurationClass.getEnclosingClass()) {
				continue;
			}
			// build requirement/s
			Requirement<?> requirement = buildRequirement(configuration, annotation);
			if (requirement.isDeclarationAcceptable()){
				return true;
			} 
		}
		return false;
	}
	
	/**
	 * Builds requirement based on given annotation and sets its configuration.
	 * @param configuration configuration instance or object, particular configuration values
	 * @param annotation requirement annotation
	 * @return requirement object with defined configuration
	 */
	@SuppressWarnings("unchecked")
	private static Requirement<?> buildRequirement(Object configuration, Annotation annotation) {
		Requirement<?> requirement = getRequirement(annotation);
		CustomConfiguration<Object> requirementConfiguraiton = (CustomConfiguration<Object>) requirement;
		requirementConfiguraiton.setConfiguration(configuration);
		return requirement;
	}
	
	/**
	 * Returns Requirement object based on given annotation and
	 * sets this annotation as requirement's declaration.
	 * @param annotation requirement annotation
	 * @return requirement object with set declaration
	 */
	@SuppressWarnings("unchecked")
	public static Requirement<?> getRequirement(Annotation annotation){
		Class<?> clazz = annotation.annotationType().getEnclosingClass();
		Requirement<Annotation> requirement = (Requirement<Annotation>) createInstance(clazz);
		requirement.setDeclaration(annotation);
		//log.debug("Found requirement " + requirement.getClass() + " for annotation " + annotation);
		return requirement;
	}

	/**
	 * Creates instance of given class object.
	 * @param clazz requirement class object to be instantiated
	 * @return creates instance of Class&lt;?&gt; object
	 */
	private static Requirement<?> createInstance(Class<?> clazz) {
		try {
			return (Requirement<?>) clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RequirementException("Error during instantiation of a requirement", e);
		} catch (IllegalAccessException e) {
			throw new RequirementException("Error during instantiation of a requirement", e);
		} catch (NullPointerException e) {
			throw new RequirementException("Error during instantiation of a null requirement object", e);
		}
	}
}
