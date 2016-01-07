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
package org.jboss.reddeer.junit.configuration.advanced;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.configuration.advanced.UserRequirement.User;
import org.jboss.reddeer.junit.requirement.CustomConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * User requirement using configuration from custom xml file
 * @author lucia jelinkova
 *
 */
public class UserRequirement implements Requirement<User>, CustomConfiguration<UserConfiguration> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface User {
		String name();
	}
	
	private User user;
	
	private UserConfiguration userConfiguration;
	
	public boolean canFulfill() {
		// return true if you can connect to the database
		return true;
	}

	public void fulfill() {
		System.out.println("fulfiling requirement User with\nName: " + user.name() +
			"\nDB name: " +	userConfiguration.getDbName() +
			"\nPort: " + userConfiguration.getPort() +
		    "\nIP: " + userConfiguration.getIp());
		// create an admin user in the database if it does not exist yet
	}
	
	public void setDeclaration(User user) {
		this.user = user;
	}
	
	public Class<UserConfiguration> getConfigurationClass() {
		return UserConfiguration.class;
	}
	
	public void setConfiguration(UserConfiguration config) {
		this.userConfiguration = config;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}
}
