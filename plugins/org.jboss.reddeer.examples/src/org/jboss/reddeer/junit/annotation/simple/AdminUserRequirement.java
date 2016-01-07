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
package org.jboss.reddeer.junit.annotation.simple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.junit.annotation.simple.AdminUserRequirement.AdminUser;
import org.jboss.reddeer.junit.requirement.Requirement;
/**
 * Admin user test requirement
 * @author lucia jelinkova
 *
 */
public class AdminUserRequirement implements Requirement<AdminUser> {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface AdminUser {
	}
	
	public boolean canFulfill() {
		// return true if you can connect to the database
		return true;
	}

	public void fulfill() {
		// create an admin user in the database if it does not exist yet
	}
	
	public void setDeclaration(AdminUser declaration) {
		// no need to access the annotation
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}
}
