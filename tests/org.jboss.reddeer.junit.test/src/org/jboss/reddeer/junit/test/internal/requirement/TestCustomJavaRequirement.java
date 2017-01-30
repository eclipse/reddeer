/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.test.internal.requirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaRequirement.CustomJavaAnnotation;

public class TestCustomJavaRequirement implements Requirement<CustomJavaAnnotation>, CustomConfiguration<TestCustomJavaConfiguration> {

	private TestCustomJavaConfiguration config;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface CustomJavaAnnotation {
	}
	
	public boolean canFulfill() {
		return true;
	}

	public void fulfill() {
	}
	
	@Override
	public void setDeclaration(CustomJavaAnnotation declaration) {
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}

	@Override
	public Class<TestCustomJavaConfiguration> getConfigurationClass() {
		return TestCustomJavaConfiguration.class;
	}

	@Override
	public void setConfiguration(TestCustomJavaConfiguration config) {
		this.config = config;
	}
	
	public TestCustomJavaConfiguration getConfig() {
		return config;
	}

}
