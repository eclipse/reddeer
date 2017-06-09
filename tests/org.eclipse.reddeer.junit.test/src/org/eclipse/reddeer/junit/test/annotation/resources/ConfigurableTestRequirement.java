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
package org.eclipse.reddeer.junit.test.annotation.resources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.test.annotation.resources.ConfigurableTestRequirement.ConfigurableTestReq;

public class ConfigurableTestRequirement implements ConfigurableRequirement<RequirementTestConfiguration, ConfigurableTestReq> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface ConfigurableTestReq {
		
	}

	@Override
	public void fulfill() {	}

	@Override
	public void setDeclaration(ConfigurableTestReq declaration) {	}

	@Override
	public ConfigurableTestReq getDeclaration() {
		return null;
	}

	@Override
	public void cleanUp() {	}

	@Override
	public Class<RequirementTestConfiguration> getConfigurationClass() {
		return null;
	}

	@Override
	public void setConfiguration(RequirementTestConfiguration configuration) { }

	@Override
	public RequirementTestConfiguration getConfiguration() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Custom req";
	}
}
