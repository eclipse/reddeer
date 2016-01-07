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

import org.jboss.reddeer.junit.annotation.simple.AdminUserRequirement.AdminUser;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@AdminUser
/**
 * Test with AdminUser requirement
 * @author lucia jelinova
 *
 */
public class AdminUserTest {
	
	@Test
	public void test(){
	  // put test logic here	
	}
}