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
}
