package org.jboss.reddeer.eclipse.ui.launcher;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.jboss.reddeer.common.properties.RedDeerProperties;

/**
 * Holds the definition of one property used in Red Deer 
 * {@link RedDeerProperties} and its current value;
 * 
 * @author Lucia Jelinkova
 *
 */
public class RedDeerLauncherProperties {

	public static final String ATTRIBUTE_PREFIX = "rd.launch.property.";

	private RedDeerProperties property;

	private String currentValue;
	
	private boolean doubleDefined = false;

	public RedDeerLauncherProperties(RedDeerProperties property) {
		this.property = property;
		currentValue = property.getDefaultValue();
	}

	/**
	 * Loads all red deer properties from config file. 
	 * @param configuration
	 * @return
	 * @throws CoreException
	 */
	public static List<RedDeerLauncherProperties> loadAll(ILaunchConfiguration configuration) throws CoreException{
		List<RedDeerLauncherProperties> properties = new ArrayList<RedDeerLauncherProperties>();

		for (String key : configuration.getAttributes().keySet()){
			
			if (key.startsWith(RedDeerLauncherProperties.ATTRIBUTE_PREFIX)){
				String argName = key.replace(RedDeerLauncherProperties.ATTRIBUTE_PREFIX, "");
				RedDeerLauncherProperties property = new RedDeerLauncherProperties(RedDeerProperties.getByName(argName));

				String argValue = configuration.getAttribute(key, property.getProperty().getDefaultValue());
				property.setCurrentValue(argValue);
				property.setDoubleDefined(configuration);;
				properties.add(property);
			}
		}

		return properties;
	}

	/**
	 * Loads value from the specified configuration
	 * @param config
	 * @throws CoreException 
	 */
	public void load(ILaunchConfiguration config) throws CoreException{
		for (String key : config.getAttributes().keySet()){
			if (key.equals(getConfigKey())){
				setCurrentValue(config.getAttribute(getConfigKey(), getProperty().getDefaultValue()));
				setDoubleDefined(config);
			}
		}
	}

	/**
	 * Resets current value to default value and removes attribute from configuration
	 * @param config
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy config){
		config.removeAttribute(getConfigKey());
		setCurrentValue(getProperty().getDefaultValue());
	}
	
	/**
	 * RedDeer property definition
	 * @return
	 */
	public RedDeerProperties getProperty() {
		return property;
	}

	/**
	 * Current value of RedDeer property
	 * @return
	 */
	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	/**
	 * Indicator for the case that the property is defined also on Arguments tab, VM arguments
	 * @param currentValue
	 */
	public boolean isDoubleDefined() {
		return doubleDefined;
	}

	/**
	 * Stores itself into the specified configuration
	 * @param config
	 */
	public void save(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(getConfigKey(), getCurrentValue());
	}

	private String getConfigKey(){
		return ATTRIBUTE_PREFIX + getProperty().getName();
	}
	
	private void setDoubleDefined(ILaunchConfiguration config) throws CoreException{
		String vmLine = config.getAttribute("org.eclipse.jdt.launching.VM_ARGUMENTS", "");
				
		if (vmLine.contains("-D" + getProperty().getName() + "=") || vmLine.matches(".*?-D" + getProperty().getName() + "\\b.*?")){
			doubleDefined = true;
		} else {
			doubleDefined = false;
		}
	}
}
