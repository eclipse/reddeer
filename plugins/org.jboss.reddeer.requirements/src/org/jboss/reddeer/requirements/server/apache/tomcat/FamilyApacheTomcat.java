/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.requirements.server.apache.tomcat;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.reddeer.requirements.server.IServerFamily;

/**
 * Server family: Apache Tomcat 
 * 
 * @author Pavol Srna
 *
 */
@XmlRootElement(name="familyApacheTomcat", namespace="http://www.jboss.org/NS/ServerReq")
public class FamilyApacheTomcat implements IServerFamily {

	private final String category = "Apache";
	
	private final String label = "Tomcat";
	
	@XmlAttribute(name="version")
	private String version;
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.requirements.server.IServerFamily#getCategory()
	 */
	@Override
	public String getCategory() {
		return category;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.requirements.server.IServerFamily#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.requirements.server.IServerFamily#getVersion()
	 */
	@Override
	public String getVersion() {
		return version;
	}
}
