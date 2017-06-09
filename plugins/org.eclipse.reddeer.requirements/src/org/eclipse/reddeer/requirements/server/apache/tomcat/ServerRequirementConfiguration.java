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

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.eclipse.reddeer.requirements.server.ServerFamily;


/**
 * Server configuration.
 * 
 * @author Pavol Srna 
 * @author mlabuda@redhat.com
 *
 */
public class ServerRequirementConfiguration implements RequirementConfiguration {
	
	private String runtime;
	
	private ServerFamily family;

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public ServerFamily getFamily() {
		return family;
	}

	public void setFamily(ServerFamily family) {
		this.family = family;
	}

	public boolean equals(Object arg) {
		if(arg == null || !(arg instanceof ServerRequirementConfiguration)) {
			return false;
		}
		if(arg == this) {
			return true;
		}
		ServerRequirementConfiguration conf = (ServerRequirementConfiguration) arg;
		return runtime.equals(conf.getRuntime()) && family.equals(conf.getFamily());
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String getId() {
		return family.getId();
	}
}