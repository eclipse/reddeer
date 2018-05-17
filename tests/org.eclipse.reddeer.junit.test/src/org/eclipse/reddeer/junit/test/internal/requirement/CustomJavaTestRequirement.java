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
package org.eclipse.reddeer.junit.test.internal.requirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.test.internal.requirement.CustomJavaTestRequirement.CustomJavaAnnotation;

public class CustomJavaTestRequirement implements ConfigurableRequirement<CustomJavaTestConfiguration, CustomJavaAnnotation> {

	private CustomJavaTestConfiguration config;
	private CustomJavaAnnotation declaration;
	
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface CustomJavaAnnotation {
	}

	@Override
	public void fulfill() {
	}
	
	@Override
	public void setDeclaration(CustomJavaAnnotation declaration) {
		this.declaration = declaration;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}

	@Override
	public Class<CustomJavaTestConfiguration> getConfigurationClass() {
		return CustomJavaTestConfiguration.class;
	}

	@Override
	public void setConfiguration(CustomJavaTestConfiguration config) {
		this.config = config;
	}
	
	public CustomJavaTestConfiguration getConfig() {
		return config;
	}

	@Override
	public CustomJavaAnnotation getDeclaration() {
		return declaration;
	}

	@Override
	public CustomJavaTestConfiguration getConfiguration() {
		return config;
	}

}
