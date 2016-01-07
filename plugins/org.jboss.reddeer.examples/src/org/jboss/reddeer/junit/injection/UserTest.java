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
package org.jboss.reddeer.junit.injection;

import org.jboss.reddeer.junit.injection.UserRequirement.User;
import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Test injecting user requirement
 * 
 * Set VM parameter -Drd.config to point to directory with requirements.xml file 
 * -Drd.config=${project_loc}/src/org/jboss/reddeer/junit/injection
 * 
 * @author lucia jelinkova
 *
 */
@RunWith(RedDeerSuite.class)
@User
public class UserTest {
	
	@InjectRequirement
	private UserRequirement userRequirement;
	
	@Test
	public void test(){
		System.out.println(userRequirement.getPassword());
	}
}