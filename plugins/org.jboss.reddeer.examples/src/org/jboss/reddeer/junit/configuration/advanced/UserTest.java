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

import org.jboss.reddeer.junit.configuration.advanced.UserRequirement.User;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * User test using configuration from custom xml file
 * 
 * Set VM parameter -Drd.config to point to directory with requirements.xml file 
 * -Drd.config=${project_loc}/src/org/jboss/reddeer/junit/configuration/advanced
 * 
 * @author lucia jelinkova
 *
 */
@RunWith(RedDeerSuite.class)
@User(name="admin")
public class UserTest {
	@Test
	public void test(){
		// put your test logic here
	}
}