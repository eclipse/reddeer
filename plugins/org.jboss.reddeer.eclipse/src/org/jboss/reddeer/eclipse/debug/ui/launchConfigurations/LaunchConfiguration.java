package org.jboss.reddeer.eclipse.debug.ui.launchConfigurations;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Represents one type of launch configuration in Launch configuration dialog. <br> 
 * Launch configuration can be selected in the left tree in the dialog. <br>
 * Launch configuration can have one or more tabs and after change, it can be applied or reverted. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class LaunchConfiguration {

	private static final Logger log = Logger.getLogger(LaunchConfiguration.class);
	
	private String type;
	
	/**
	 * Construct configuration for the specified category
	 * @param category Category name
	 */
	public LaunchConfiguration(String category) {
		this.type = category;
	}
	
	/**
	 * Apply the current configuration
	 */
	public void apply(){
		log.info("Apply the launch configuration");

		Button button = new PushButton("Apply");
		button.click();

		new WaitWhile(new JobIsRunning());
	}

	/**
	 * Revert the launch configuration
	 */
	public void revert(){
		log.info("Revert the launch configuration");

		Button button = new PushButton("Revert");
		button.click();

		new WaitWhile(new JobIsRunning());
	}
	
	/**
	 * Return the name of the launch configuration
	 * @return Name of the launch configuration
	 */
	public String getName() {
		return new LabeledText("Name:").getText();
	}
	
	/**
	 * Set the name of the launch configuration
	 * @param name Name of the launch configuration
	 */
	public void setName(String name) {
		new LabeledText("Name:").setText(name);
	}

	/**
	 * Return the type of the launch configuration (e.g JUnit, Maven)
	 * @return
	 */
	public String getType() {
		return type;
	}
}
