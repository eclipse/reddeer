package org.jboss.reddeer.requirements.jre;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.eclipse.jdt.ui.preferences.JREsPreferencePage;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.requirements.jre.JRERequirement.JRE;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;

/**
 * Requirement for specific JRE. This requirement will add new JRE to eclipse
 * using Preferences->Java->Installed JRE's
 * 
 * @author rhopp
 *
 */

public class JRERequirement implements Requirement<JRE>, CustomConfiguration<JREConfiguration> {

	private Logger log = Logger.getLogger(JRERequirement.class);
	private JRE jre;
	private JREConfiguration configuration;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface JRE {
		
		/**
		 * Value.
		 *
		 * @return the double
		 */
		double value() default -1;

		/**
		 * Cleanup.
		 *
		 * @return true, if successful
		 */
		boolean cleanup() default false;
	}

	/**
	 * This requirement can be fulfilled, when versions configured in
	 * configuration file and annotation are matching and when New JRE wizard
	 * throws no error.
	 *
	 * @return true, if successful
	 */

	@Override
	public boolean canFulfill() {
		if ((jre.value() > 0) && (jre.value() != Double.valueOf(configuration.getVersion()))) {
			log.error("Unable to fulfill JRE requirement: Configured JRE version(" + configuration.getVersion()
					+ ") does not match version in JRE annotation(" + jre.value() + ").");
			return false;
		}
		new WaitWhile(new JobIsRunning());
		log.info("JRE Requirement canFulfill performed");
		log.debug("Configuration (name,version,path): %s, %s, %s", configuration.getName(), configuration.getVersion(),
				configuration.getPath());
		JREsPreferencePage page = new JREsPreferencePage();
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		dialog.open();
		dialog.select(page);
		String errorMessage = page.validateJRELocation(configuration.getPath(), configuration.getName());
		if (errorMessage == null) {
			return true;
		} else {
			log.error("Unable to fulfill JRE requirement: " + errorMessage);
			return false;
		}
	}

	/**
	 * Adds new JRE using Preferences->Java->Installed JRE's, Add JRE wizard.
	 */
	@Override
	public void fulfill() {
		log.info("JRE Requirement fulfill performed");
		log.debug("Configuration (name,version,path): %s, %s, %s", configuration.getName(), configuration.getVersion(),
				configuration.getPath());

		JREsPreferencePage page = new JREsPreferencePage();
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		dialog.open();
		dialog.select(page);
		page.addJRE(configuration.getPath(), configuration.getName());
		dialog.ok();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.Requirement#setDeclaration(java.lang.annotation.Annotation)
	 */
	@Override
	public void setDeclaration(JRE declaration) {
		this.jre = declaration;

	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {
		if (jre.cleanup()) {
			JREsPreferencePage page = new JREsPreferencePage();
			WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
			dialog.open();
			dialog.select(page);
			page.deleteJRE(configuration.getName());
			dialog.ok();
		}
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.CustomConfiguration#getConfigurationClass()
	 */
	@Override
	public Class<JREConfiguration> getConfigurationClass() {
		return JREConfiguration.class;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.CustomConfiguration#setConfiguration(java.lang.Object)
	 */
	@Override
	public void setConfiguration(JREConfiguration config) {
		this.configuration = config;

	}

	/**
	 * Gets the JRE path.
	 *
	 * @return the JRE path
	 */
	public String getJREPath() {
		return configuration.getPath();
	}

	/**
	 * Gets the JRE name.
	 *
	 * @return the JRE name
	 */
	public String getJREName() {
		return configuration.getName();
	}

	/**
	 * Gets the JRE version.
	 *
	 * @return the JRE version
	 */
	public double getJREVersion() {
		return configuration.getVersion();
	}

}
