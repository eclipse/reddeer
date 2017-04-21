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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "jre-requirement", namespace = "http://www.jboss.org/NS/jre-schema")
public class TestCustomJavaConfiguration {

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

		
		private static String safeProperty(String s) {
			if (s != null && s.startsWith("${") && s.endsWith("}")) {
				String key = s.substring(2, s.length() - 1);
				String v = System.getProperty(key);
				if (v != null) {
					return v;
				}
			}

			return s;
		}
		
		/**
		 * Sets name of this JRE used in eclipse.
		 * @param name Name of this JRE.
		 */
		@XmlElement(namespace = "http://www.jboss.org/NS/jre-schema")
		public void setName(String name) {
			this.name = safeProperty(name);
		}

		
		/**
		 * Sets path to JRE.
		 * @param path Path to JRE.
		 */
		@XmlElement(namespace = "http://www.jboss.org/NS/jre-schema")
		public void setPath(String path) {
			this.path = safeProperty(path);
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
