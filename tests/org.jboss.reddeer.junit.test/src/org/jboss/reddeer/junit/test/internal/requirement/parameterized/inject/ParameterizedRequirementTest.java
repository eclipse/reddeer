/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.junit.test.internal.requirement.parameterized.inject;

import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import static org.junit.Assert.*;

public class ParameterizedRequirementTest {

	@Test
	public void testStaticInject() throws InitializationError {
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
		RunNotifier notifier = new RunNotifier();
		RedDeerSuite rs = new RedDeerSuite(ParameterizedStaticReqTestClazz.class, new AllDefaultPossibilitiesBuilder(true));
		rs.run(notifier);
		assertTrue(ParameterizedStaticReqTestClazz.getReq() != null);
	}

}
