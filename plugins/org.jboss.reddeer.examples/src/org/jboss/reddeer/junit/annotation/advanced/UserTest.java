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
package org.jboss.reddeer.junit.annotation.advanced;

import org.jboss.reddeer.junit.annotation.advanced.UserRequirement.User;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@User(name="admin")
/**
 * Test with parameterized requirement User
 * @author lucia jelinkova
 *
 */
public class UserTest {
	
	@Test
	public void test(){
		// put test logic here
	}
}