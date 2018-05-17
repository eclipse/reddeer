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

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

public class SimpleConfiguration implements RequirementConfiguration {

	private String name;
	private String version;
	private String type;
	
	public SimpleConfiguration() {
	}
	
	public SimpleConfiguration(SimpleConfiguration simpleConfig) {
		name = simpleConfig.getName();
		version = simpleConfig.getVersion();
		type = simpleConfig.getType();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String getId() {
		return name + "-" + "version" + "-" + type;
	}
	
}
