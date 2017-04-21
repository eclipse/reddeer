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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.reddeer.requirements.server.IServerFamily;
import org.eclipse.reddeer.requirements.server.apache.tomcat.FamilyApacheTomcat;

@XmlRootElement(name="server-requirement", namespace="http://www.jboss.org/NS/ServerReq")
public class TestCustomServerConfiguration {
	
	private String runtime;
	
	@XmlElementWrapper(name="type", namespace="http://www.jboss.org/NS/ServerReq")
	@XmlElements({
		@XmlElement(name="familyApacheTomcat", namespace="http://www.jboss.org/NS/ServerReq", type = FamilyApacheTomcat.class)
	})
	private List<IServerFamily> family;
	
	public IServerFamily getServerFamily() {
		return this.family.get(0); //always: size() == 1 
	}
	
	public String getRuntime() {
		return runtime;
	}

	/**
	 * Sets the runtime.
	 *
	 * @param runtime the new runtime
	 */
	@XmlElement(namespace="http://www.jboss.org/NS/ServerReq")
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
}
