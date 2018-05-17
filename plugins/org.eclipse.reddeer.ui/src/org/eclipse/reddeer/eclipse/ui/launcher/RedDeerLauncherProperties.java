/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.ui.launcher;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.eclipse.jdt.debug.ui.launchConfigurations.RedDeerJavaArgumentsTab;

/**
 * Holds the definition of one property used in RedDeer 
 * {@link RedDeerProperties} and its current value;
 * 
 * @author Lucia Jelinkova
 *
 */
public class RedDeerLauncherProperties {

	public static final String ATTRIBUTE_PREFIX = "rd.launch.property.";
	// each reddeer parameter must start with 'rd', e.g. 'rd.disableMavenIndex'
	public static final String REDDEER_PARAMETER_PREFIX = "rd.";
	public static final String REDDEER_OLD_PARAMETER_PREFIX = "reddeer.";

	private RedDeerProperties property;

	private String currentValue;
	
	private boolean doubleDefined = false;

	public RedDeerLauncherProperties(RedDeerProperties property) {
		this.property = property;
		currentValue = property.getValue();
	}

	/**
	 * Loads all red deer properties from config file. 
	 * 
	 * @param configuration configuration
	 * @return list of RedDeer launcher properties
	 * @throws CoreException if something goes wrong
	 */
	public static List<RedDeerLauncherProperties> loadAll(ILaunchConfiguration configuration) throws CoreException{
		List<RedDeerLauncherProperties> properties = new ArrayList<RedDeerLauncherProperties>();

		for (String key : configuration.getAttributes().keySet()){
			
			if (key.startsWith(RedDeerLauncherProperties.ATTRIBUTE_PREFIX)){
				String argName = key.replace(RedDeerLauncherProperties.ATTRIBUTE_PREFIX, "");
				try{
					RedDeerProperties rdProperty = getByName(argName);
					RedDeerLauncherProperties property = new RedDeerLauncherProperties(rdProperty);
					String argValue = configuration.getAttribute(key, property.getProperty().getValue());
					property.setCurrentValue(argValue);
					property.setDoubleDefined(configuration);
					properties.add(property);
				} catch (RedDeerException re){
					// RedDeer property defined in launch configuration doesn't exist or have wrong value
					// just do not use it
				}
			}
		}

		return properties;
	}
	
	private static RedDeerProperties getByName(String name){
		if (name.startsWith(REDDEER_PARAMETER_PREFIX)){
			return RedDeerProperties.getByName(name);
		} else {
			return RedDeerProperties.getByName(name.replace(REDDEER_OLD_PARAMETER_PREFIX, REDDEER_PARAMETER_PREFIX));
		}
	}

	/**
	 * Loads value from the specified configuration
	 * @param config config to load
	 * @throws CoreException if something goes wrong
	 */
	public void load(ILaunchConfiguration config) throws CoreException{
		for (String key : config.getAttributes().keySet()){
			if (key.equals(getConfigKey())){
				setCurrentValue(config.getAttribute(key, getProperty().getValue()));
				setDoubleDefined(config);
			}
		}
	}

	/**
	 * Resets current value to default value and removes attribute from configuration
	 * @param config config to set to default
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy config){
		config.removeAttribute(getConfigKey());
		setCurrentValue(getProperty().getDefaultValue());
	}
	
	/**
	 * RedDeer property definition
	 * 
	 * @return RedDeer properties
	 */
	public RedDeerProperties getProperty() {
		return property;
	}

	/**
	 * Gets current value of RedDeer property
	 * 
	 * @return get current value of RedDeer property
	 */
	public String getCurrentValue() {
		return currentValue;
	}

	/**
	 * Sets value of RedDeer property
	 * 
	 * @param newValue set new value to current value
	 */
	public void setCurrentValue(String newValue) {
		this.currentValue = newValue;
	}

	/**
	 * Indicator for the case that the property is defined also on Arguments tab, VM arguments
	 * 
	 * @return true if is double defined, false otherwise
	 */
	public boolean isDoubleDefined() {
		return doubleDefined;
	}

	/**
	 * Stores itself into the specified configuration
	 * 
	 * @param config config to save
	 */
	public void save(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(getConfigKey(), getCurrentValue());
	}

	private String getConfigKey(){
		return ATTRIBUTE_PREFIX + getProperty().getName();
	}
	
	private void setDoubleDefined(ILaunchConfiguration config) throws CoreException{
		String vmLine = config.getAttribute(RedDeerJavaArgumentsTab.VM_ARGS_ATTR_NAME, "");
				
		if (vmLine.contains("-D" + getProperty().getName() + "=") || vmLine.matches(".*?-D" + getProperty().getName() + "\\b.*?")){
			doubleDefined = true;
		} else {
			doubleDefined = false;
		}
	}
	
	/**
	 * Returns initial RedDeer Launcher properties
	 * 
	 * @return array of RedDeer launcher properities
	 */
	static RedDeerLauncherProperties[] getInitialRedDeerLauncherProperties(){
		RedDeerProperties[] properties = RedDeerProperties.values();
		RedDeerLauncherProperties[] tabProperties = new RedDeerLauncherProperties[properties.length];

		for (int i = 0; i < properties.length; i++){
			tabProperties[i] = new RedDeerLauncherProperties(properties[i]);
		}
		return tabProperties;
	}
}
