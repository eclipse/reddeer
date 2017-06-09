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
package org.eclipse.reddeer.junit.test.requirement.configuration.resources;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

public class ComplexConfiguration implements RequirementConfiguration {

	private String complexName;
	private String complexType;
	private String complexVersion;
	private SimpleConfiguration simpleConfiguration;
	
	public ComplexConfiguration() {
	}

	public ComplexConfiguration(ComplexConfiguration config) {
		complexName = config.getComplexName();
		complexType = config.getComplexType();
		complexVersion = config.getComplexVersion();
		simpleConfiguration = new SimpleConfiguration(config.getSimpleConfiguration());
	}
	
	public String getComplexName() {
		return complexName;
	}
	public void setComplexName(String complexName) {
		this.complexName = complexName;
	}
	public String getComplexType() {
		return complexType;
	}
	public void setComplexType(String complexType) {
		this.complexType = complexType;
	}
	public String getComplexVersion() {
		return complexVersion;
	}
	public void setComplexVersion(String complexVersion) {
		this.complexVersion = complexVersion;
	}
	public SimpleConfiguration getSimpleConfiguration() {
		return simpleConfiguration;
	}
	public void setSimpleConfiguration(SimpleConfiguration simpleConfiguration) {
		this.simpleConfiguration = simpleConfiguration;
	}
	@Override
	public String getId() {
		return complexName + "-" + complexType + "-" + complexVersion + simpleConfiguration.getId(); 
	}
	
}
