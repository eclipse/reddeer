package org.jboss.reddeer.common.properties;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.logging.LoggingUtils;
import org.jboss.reddeer.common.userprofile.UserProfile;

/**
 * Enumeration of all system properties that could be set to RedDeer.
 * 
 * @author Lucia Jelinkova
 *
 */
public enum RedDeerProperties {

	PAUSE_FAILED_TEST("reddeer.pauseFailedTest", false),

	LOG_MESSAGE_FILTER("reddeer.logMessageFilter", "ALL"),

	LOG_LEVEL("reddeer.logLevel", "ALL"),

	CLOSE_WELCOME_SCREEN("reddeer.close.welcome.screen", true),

	CLOSE_ALL_SHELLS("reddeer.close.shells", true),

	DISABLE_MAVEN_REPOSITORY_DOWNLOAD("reddeer.disable.maven.download.repo.index.on.startup", true),

	/**
	 * System property pointing either to the configuration file or to the configuration directory. 
	 */
	CONFIG_FILE("reddeer.config", (String) null),

	CAPTURE_SCREENSHOT("reddeer.captureScreenshot", true),

	RECORD_SCREENCAST("reddeer.recordScreenCast", false),

	RELATIVE_SCREENSHOT_DIRECTORY("reddeer.relativeScreenshotDirectory", (String) null),

	OPEN_ASSOCIATED_PERSPECTIVE("reddeer.set.open.associated.perspective", "never"),

	TIME_PERIOD_FACTOR("reddeer.time.period.factor", 1.f);

	private static final Logger log = Logger.getLogger(RedDeerProperties.class);

	private String name;

	private RedDeerPropertyType type;

	private String defaultValue;

	private String[] supportedValues;

	private RedDeerProperties(String name, Boolean defaultValue){
		this.name = name;
		this.type = RedDeerPropertyType.BOOLEAN;
		this.defaultValue = defaultValue.toString();
		this.supportedValues = new String[]{"true", "false"};
		checkSystemValue(getValueInternal());
	}

	private RedDeerProperties(String name, String defaultValue){
		this.name = name;
		this.type = RedDeerPropertyType.TEXT;
		this.defaultValue = defaultValue;
		this.supportedValues = new String[0];
	}

	private RedDeerProperties(String name, String defaultValue, String... supportedValues){
		this.name = name;
		this.type = RedDeerPropertyType.ENUMERATION;
		this.defaultValue = defaultValue;
		this.supportedValues = supportedValues;
		checkSystemValue(getValueInternal());
	}

	private RedDeerProperties(String name, Float defaultValue){
		this.name = name;
		this.type = RedDeerPropertyType.FLOAT;
		this.defaultValue = defaultValue.toString();
		this.supportedValues = new String[0];
		checkSystemValue(getValueInternal());
	}

	/**
	 * Returns the enumeration by its name.
	 * @param name
	 * @return
	 */
	public static RedDeerProperties getByName(String name){
		for (RedDeerProperties property : RedDeerProperties.values()){
			if (property.getName().equals(name)){
				return property;
			}
		}
		throw new RedDeerException("RedDeerProperties enumeration with name " + name + " does not exist");
	}

	/**
	 * Retrieves the property from the currently running system and user and 
	 * checks if the value is between supported values. If the property is not defined in system, 
	 * returns default value. 
	 * @return
	 */
	public String getValue(){
		String value = getValueInternal();
		checkSystemValue(value);
		return value;
	}

	/**
	 * Returns {@link #getValue()} converted to the boolean. 
	 * @return
	 */
	public boolean getBooleanValue(){
		if (getType() != RedDeerPropertyType.BOOLEAN){
			throw new RedDeerException("Requested boolean system value from non boolean property [" 
					+ getName() + ", " + getType() + "]");
		}
		String propertyValue = getValue();
		return Boolean.parseBoolean(propertyValue.toLowerCase());
	}
	
	/**
	 * Returns {@link #getValue()} converted to the float. 
	 * @return
	 */
	public float getFloatValue(){
		if (getType() != RedDeerPropertyType.FLOAT){
			throw new RedDeerException("Requested float system value from non float property [" 
					+ getName() + ", " + getType() + "]");
		}
		String propertyValue = getValue();
		return Float.parseFloat(propertyValue.toLowerCase());
	}

	/**
	 * Property name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Property type
	 * @return
	 */
	public RedDeerPropertyType getType() {
		return type;
	}

	/**
	 * Default value of the property
	 * @return
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * If the property can have only specific values returns those values. 
	 * @return
	 */
	public String[] getSupportedValues() {
		return supportedValues.clone();
	}

	/**
	 * Checks if the defined system value is of supported value
	 */
	private void checkSystemValue(String systemValue) {
		if (getType() == RedDeerPropertyType.TEXT){
			return;
		}

		if (getType() == RedDeerPropertyType.FLOAT) {
			try {
				Float.parseFloat(systemValue);
				return;
			} catch (Exception e) {
				log.error("System property '" + getName() + "' has unsupported value '" 
						+ systemValue + "'. Value has to be floating point number");
				throw new RedDeerException("System property '" + getName() + "' has unsupported value '" 
						+ systemValue + "'. Value has to be floating point number");
			}
		}

		if (getType() == RedDeerPropertyType.BOOLEAN || getType() == RedDeerPropertyType.ENUMERATION){
			systemValue = systemValue.toLowerCase();
		}

		for (String supportedValue : getSupportedValues()){
			if (systemValue == null || supportedValue.equals(systemValue)){
				return;
			}
		}

		log.error("System property '" + getName() + "' has unsupported value '" 
				+ systemValue + "'. Supported values are: " + LoggingUtils.format(getSupportedValues()));
		throw new RedDeerException("System property '" + getName() + "' has unsupported value '" 
				+ systemValue + "'. Supported values are: " + LoggingUtils.format(getSupportedValues()));
	}

	private String getValueInternal(){
		String value = System.getProperty(getName());
		if (value == null){
			value = UserProfile.getInstance().getProperty(getName());
			if (value == null){
				value = getDefaultValue();
			}
		}

		return value;
	}
}
