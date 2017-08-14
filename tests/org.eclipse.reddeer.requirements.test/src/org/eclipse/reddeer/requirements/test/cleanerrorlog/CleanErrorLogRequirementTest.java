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
package org.eclipse.reddeer.requirements.test.cleanerrorlog;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.reddeer.eclipse.ui.views.log.LogView;
import org.eclipse.reddeer.junit.internal.configuration.RequirementConfigurationSet;
import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.cleanerrorlog.CleanErrorLogRequirement.CleanErrorLog;
import org.eclipse.reddeer.requirements.test.Activator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests the requirement for cleaning Error Log.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
@RunWith(RedDeerSuite.class)
public class CleanErrorLogRequirementTest {

	@Test
	public void testTestClassWithCleaningErrorLog() {
		ILog log = Platform.getLog(Platform.getBundle(Activator.PLUGIN_ID));
		log.log(new Status(IStatus.ERROR, "ERROR_1234", "Error 1234", new NullPointerException("NPE_1234")));

		getRequirements(TestClassWithCleaningErrorLog.class).fulfill();

		new LogView().open();
		Assert.assertTrue(new LogView().getErrorMessages().isEmpty());
	}

	@Test
	public void testTestClassWithoutCleaningErrorLog() {
		ILog log = Platform.getLog(Platform.getBundle(Activator.PLUGIN_ID));
		log.log(new Status(IStatus.ERROR, "ERROR_1234", "Error 1234", new NullPointerException("NPE_1234")));

		getRequirements(TestClassWithoutCleaningErrorLog.class).fulfill();

		new LogView().open();
		Assert.assertFalse(new LogView().getErrorMessages().isEmpty());
	}

	private Requirements getRequirements(Class<?> klass) {
		RequirementsBuilder reqBuilder = new RequirementsBuilder();
		RequirementConfigurationSet configSet = new RequirementConfigurationSet();
		return reqBuilder.build(configSet, klass);
	}

	@CleanErrorLog
	@RunWith(RedDeerSuite.class)
	static class TestClassWithCleaningErrorLog {

		public TestClassWithCleaningErrorLog() {

		}

		@Test
		public void test() {

		}
	}

	@RunWith(RedDeerSuite.class)
	static class TestClassWithoutCleaningErrorLog {

		public TestClassWithoutCleaningErrorLog() {

		}

		@Test
		public void test() {

		}
	}

}
