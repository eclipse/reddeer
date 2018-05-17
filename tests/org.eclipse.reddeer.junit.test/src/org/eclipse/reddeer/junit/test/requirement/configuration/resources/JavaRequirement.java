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
package org.eclipse.reddeer.junit.test.requirement.configuration.resources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.junit.test.requirement.configuration.resources.JavaRequirement.CustomConfigJavaRequirementAAnnotation;

public class JavaRequirement implements ConfigurableRequirement<JavaRequirementConfig, CustomConfigJavaRequirementAAnnotation> {

	private JavaRequirementConfig config;
	
	private CustomConfigJavaRequirementAAnnotation declaration;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface CustomConfigJavaRequirementAAnnotation {
		
	}

	@Override
	public void setDeclaration(CustomConfigJavaRequirementAAnnotation a) {
		declaration = a;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CustomConfigJavaRequirementAAnnotation getDeclaration() {
		return declaration;
	}

	@Override
	public JavaRequirementConfig getConfiguration() {
		return config;
	}

	@Override
	public void fulfill() {
		// do nothing
	}

	@Override
	public Class<JavaRequirementConfig> getConfigurationClass() {
		return JavaRequirementConfig.class;
	}

	@Override
	public void setConfiguration(JavaRequirementConfig config) {
		this.config = config;
	}
}
