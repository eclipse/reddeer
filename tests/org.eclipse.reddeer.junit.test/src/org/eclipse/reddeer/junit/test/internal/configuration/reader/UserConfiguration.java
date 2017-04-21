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
package org.eclipse.reddeer.junit.test.internal.configuration.reader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user-requirement", namespace="http://www.jboss.org/NS/user-schema")
public class UserConfiguration {

        private String dbName;
        
        private String ip;
        
        private String port;

        public String getIp() {
                return ip;
        }

        @XmlElement(namespace="http://www.jboss.org/NS/user-schema")
        public void setIp(String ip) {
                this.ip = ip;
        }

        public String getPort() {
                return port;
        }

        @XmlElement(namespace="http://www.jboss.org/NS/user-schema")
        public void setPort(String port) {
                this.port = port;
        }

        public String getDbName() {
                return dbName;
        }

        @XmlElement(name="db-name", namespace="http://www.jboss.org/NS/user-schema")
        public void setDbName(String dbName) {
                this.dbName = dbName;
        }
}