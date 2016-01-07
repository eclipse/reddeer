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
package org.jboss.reddeer.swt.test.condition;

import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.core.condition.ConstructorFinishedSuccessfully;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Tests ConstructorFinishedSuccessfully condition
 * @author Vlado Pakan
 *
 */
@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class ConstructorFinishedSuccessfullyTest {
	
	@Test
	public void constructorRunOK(){
		new WaitUntil(new ConstructorFinishedSuccessfully(DefaultShell.class,
			null,
			null));
	}
	
	@Test(expected = WaitTimeoutExpiredException.class)
	public void constructorFailed(){
		new WaitUntil(new ConstructorFinishedSuccessfully(DefaultShell.class,
			new Class<?>[] {String.class},
			new Object[] {"!@#_NON_EXISTING_SHELL_*&^%"}));
	}
}



