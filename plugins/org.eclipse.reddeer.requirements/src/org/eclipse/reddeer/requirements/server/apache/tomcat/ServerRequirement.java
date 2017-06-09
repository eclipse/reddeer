/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.requirements.server.apache.tomcat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewServerWizard;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardPage;
import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.requirements.property.RequirementPropertyExpandor;
import org.eclipse.reddeer.requirements.server.ConfiguredServerInfo;
import org.eclipse.reddeer.requirements.server.ServerReqBase;
import org.eclipse.reddeer.requirements.server.ServerReqState;
import org.eclipse.reddeer.requirements.server.apache.tomcat.ServerRequirement.ApacheTomcatServer;
import org.eclipse.reddeer.swt.impl.text.DefaultText;

/**
 * 
 * @author Pavol Srna
 *
 */
public class ServerRequirement extends ServerReqBase
		implements ConfigurableRequirement<ServerRequirementConfiguration, ApacheTomcatServer> {

	private static final Logger LOGGER = Logger.getLogger(ServerRequirement.class);

	private ServerRequirementConfiguration config;
	private static ConfiguredServerInfo lastServerConfiguration;
	private ApacheTomcatServer server;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface ApacheTomcatServer {

		/**
		 * State.
		 *
		 * @return the server req state
		 */
		ServerReqState state()

		default ServerReqState.RUNNING;

		/**
		 * Cleanup.
		 *
		 * @return true, if successful
		 */
		boolean cleanup() default true;
	}

	@Override
	public void fulfill() {
		if (lastServerConfiguration == null || !isLastConfiguredServerPresent()) {
			LOGGER.info("Setup server");
			setupServerAdapter();
			lastServerConfiguration = new ConfiguredServerInfo(getServerNameLabelText(), getRuntimeNameLabelText(),
					config);
		}
		setupServerState(server.state());
	}

	@Override
	public String getServerTypeLabelText() {
		return config.getFamily().getLabel() + " v" + config.getFamily().getVersion() + " Server";
	}

	@Override
	public String getServerNameLabelText() {
		return getServerTypeLabelText() + " at localhost";
	}

	@Override
	public String getRuntimeNameLabelText() {
		return config.getFamily().getCategory() + " " + config.getFamily().getLabel() + " v"
				+ config.getFamily().getVersion() + " Runtime";
	}

	private void setupServerAdapter() {

		NewServerWizard swd = new NewServerWizard();
		swd.open();

		NewServerWizardPage swpage = new NewServerWizardPage();

		swpage.selectType(config.getFamily().getCategory(), getServerTypeLabelText());
		swpage.setName(getServerNameLabelText());
		swd.next();

		new DefaultText(0).setText(getRuntimeNameLabelText());
		new DefaultText(1).setText(getRuntimeName());

		swd.finish();
	}

	/**
	 * Gets runtime name. If name is a property in configuration, then it is
	 * expanded to its real value. Otherwise value in configuration is returned.
	 * 
	 * @return runtime name, if it is property, then expanded, otherwise String
	 *         from configuration
	 */
	public String getRuntimeName() {
		return RequirementPropertyExpandor.getProperty(getConfiguration().getRuntime());
	}

	@Override
	public void setDeclaration(ApacheTomcatServer server) {
		this.server = server;
	}

	@Override
	public Class<ServerRequirementConfiguration> getConfigurationClass() {
		return ServerRequirementConfiguration.class;
	}

	@Override
	public void setConfiguration(ServerRequirementConfiguration config) {
		this.config = config;
	}

	@Override
	public void cleanUp() {
		if (server.cleanup() && config != null) {
			removeServerAndRuntime(lastServerConfiguration);
			lastServerConfiguration = null;
		}
	}

	@Override
	public ConfiguredServerInfo getConfiguredConfig() {
		return lastServerConfiguration;
	}

	@Override
	public ApacheTomcatServer getDeclaration() {
		return server;
	}

	@Override
	public ServerRequirementConfiguration getConfiguration() {
		return config;
	}

	@Override
	public String getDescription() {
		return config.getId();
	}

	@Override
	public ServerRequirementConfiguration getConfig() {
		return config;
	}
}