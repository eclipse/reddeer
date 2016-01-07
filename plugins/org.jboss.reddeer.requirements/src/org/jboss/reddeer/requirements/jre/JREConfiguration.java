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
package org.jboss.reddeer.requirements.jre;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class contains JRE configuration used by JRE requirement
 * that is configurable from outside via Requirement feature. 
 *
 */

@XmlRootElement(name = "jre-requirement", namespace = "http://www.jboss.org/NS/jre-schema")
public class JREConfiguration {
	
	private String name;
	
	private String path;
	
	private double version;

	/**
	 * Gets name of this requirement to be used in eclipse.
	 * @return JRE name
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * Gets path of where the JRE is installed .
	 * @return path to JRE
	 */
	public String getPath() {
		return path;
	}

	
	/**
	 * Gets version of JRE.
	 *
	 * @return the version
	 */
	public double getVersion() {
		return version;
	}

	
	/**
	 * Sets name of this JRE used in eclipse.
	 * @param name Name of this JRE.
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/jre-schema")
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * Sets path to JRE.
	 * @param path Path to JRE.
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/jre-schema")
	public void setPath(String path) {
		this.path = path;
	}

	
	/**
	 * Sets version of JRE.
	 * @param version Version of JRE.
	 */
	@XmlElement(namespace = "http://www.jboss.org/NS/jre-schema")
	public void setVersion(double version) {
		this.version = version;
	}
}
