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
package org.eclipse.reddeer.junit.test.integration.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.junit.requirement.PropertyConfiguration;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.test.integration.configuration.RequirementB.RequirementBAnnotation;

public class RequirementB implements Requirement<RequirementBAnnotation>, PropertyConfiguration {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface RequirementBAnnotation {
	}	
	
	private String a;
	
	private String b;
	
	public boolean canFulfill() {
		return false;
	}

	public void fulfill() {
	}
	
	public void setA(String a) {
		this.a = a;
	}
	
	public void setB(String b) {
		this.b = b;
	}
	
	public String getA() {
		return a;
	}
	
	public String getB() {
		return b;
	}
	
	@Override
	public void setDeclaration(RequirementBAnnotation declaration) {
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}
}
