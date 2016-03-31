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
package org.jboss.reddeer.junit.test.integration.runner.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.execution.TestMethodShouldRun;
import org.jboss.reddeer.junit.execution.annotation.RunIf;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.requirement.PropertyConfiguration;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.junit.test.integration.runner.injection.InjectRequirementsBeforRunIfTest.ARequirementImpl.ARequirement;
import org.jboss.reddeer.junit.test.integration.runner.injection.InjectRequirementsBeforRunIfTest.RedDeerSuiteExt;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * 
 * @author apodhrad
 *
 */
@ARequirement
@RunWith(RedDeerSuiteExt.class)
public class InjectRequirementsBeforRunIfTest {

	@InjectRequirement
	public static ARequirementImpl aRequirement;

	@Test
	@RunIf(conditionClass = ACondition.class)
	public void test() {
	}

	public static class RedDeerSuiteExt extends RedDeerSuite {

		protected static final String LOCATIONS_ROOT_DIR = "resources/org/jboss/reddeer/junit/test/integration/runner/injection";

		public RedDeerSuiteExt(Class<?> clazz, RunnerBuilder builder) throws InitializationError {
			super(heck(clazz), builder);
		}

		protected RedDeerSuiteExt(Class<?> clazz, RunnerBuilder builder, SuiteConfiguration config)
				throws InitializationError {
			super(clazz, builder, config);
		}

		/**
		 * Hecky hook for setting the system property.
		 * 
		 * @param clazz
		 * @return
		 */
		private static Class<?> heck(Class<?> clazz) {
			System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), LOCATIONS_ROOT_DIR);
			return clazz;
		}
	}

	public static class ARequirementImpl implements Requirement<ARequirement>, PropertyConfiguration {

		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.TYPE)
		public static @interface ARequirement {

		}

		private String a;

		public boolean canFulfill() {
			return true;
		}

		public void fulfill() {

		}

		@Override
		public void setDeclaration(ARequirement aRequirement) {

		}

		@Override
		public void cleanUp() {

		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

	}

	public static class ACondition implements TestMethodShouldRun {

		@Override
		public boolean shouldRun(FrameworkMethod method) {
			ARequirementImpl aRequirement = InjectRequirementsBeforRunIfTest.aRequirement;
			if (aRequirement == null) {
				Assert.fail("@RunIf is not evaluated after injecting all requirements, see #1368");
			}
			return true;
		}

	}

}
