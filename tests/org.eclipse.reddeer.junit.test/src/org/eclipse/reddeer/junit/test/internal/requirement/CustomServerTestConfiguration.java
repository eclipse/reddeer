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
package org.eclipse.reddeer.junit.test.internal.requirement;

import org.eclipse.reddeer.junit.requirement.configuration.RequirementConfiguration;
import org.eclipse.reddeer.requirements.server.ServerFamily;

public class CustomServerTestConfiguration implements RequirementConfiguration {
	
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

	@Override
	public String getId() {
		return "server-" + runtime;
	}
	
}
