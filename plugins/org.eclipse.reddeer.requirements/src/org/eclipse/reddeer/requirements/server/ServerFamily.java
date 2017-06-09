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
package org.eclipse.reddeer.requirements.server;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;

public class ServerFamily implements RequirementConfiguration {

	public static final String CATEGORY_TOMCAT = "Apache";
	public static final String LABEL_TOMCAT = "Tomcat";

	public String category;
	public String label;
	/**
	 * Version should be decimal number without micro version. E.g. 7.0 or 6.0
	 */
	public String version;

	public ServerFamily() {

	}

	public ServerFamily(ServerFamily family) {
		this.category = family.getCategory();
		this.label = family.getLabel();
		this.version = family.getVersion();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		return category.hashCode() + version.hashCode() + label.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ServerFamily)) {
			return false;
		}
		ServerFamily family = (ServerFamily) obj;
		return category.equals(family.getCategory()) && version.equals(family.version)
				&& label.equals(family.getLabel());
	}

	@Override
	public String getId() {
		return category + "-" + label + "-" + version;
	}
}
