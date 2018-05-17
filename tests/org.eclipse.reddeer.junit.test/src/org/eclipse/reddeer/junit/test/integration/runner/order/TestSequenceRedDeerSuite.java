/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.integration.runner.order;

import java.util.Iterator;
import java.util.List;

import org.eclipse.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import junit.framework.AssertionFailedError;

public abstract class TestSequenceRedDeerSuite extends RedDeerSuite {

	public TestSequenceRedDeerSuite(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(clazz, builder);
	}

	protected TestSequenceRedDeerSuite(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(clazz, builder, config);
	}
	
	protected abstract List<?> getExpectedSequence();
	
	@Override
	public void run(RunNotifier notifier) {
		TestSequence.getRealSequence().clear();
		super.run(notifier);
		if (!getExpectedSequence().equals(TestSequence.getRealSequence())){
			notifier.fireTestFailure(new Failure(Description.createSuiteDescription(this.getClass()), createException()));
		}
	}

	private Throwable createException() {
		List<?> realList = TestSequence.getRealSequence();
		List<?> expectedList = getExpectedSequence();
		Iterator<?> realSequenceIterator = realList.iterator();
		Iterator<?> expectedSequenceIterator = expectedList.iterator();
		
		int i = 0;
		while (realSequenceIterator.hasNext() && expectedSequenceIterator.hasNext()){
			Object real = realSequenceIterator.next();
			Object expected = expectedSequenceIterator.next();
			if (!real.equals(expected)){
				return new AssertionFailedError("Expected and real sequence differ at index " + i + ".  Expected: " + expected + ", real: " + real
						+ "\n" + "Whole sequences: \n" + "REAL:" + realList + "\n" + "EXPECTED:" + expectedList);
			}
			i++;
		}
		if (realSequenceIterator.hasNext()){
			return new AssertionFailedError("Real sequence contains more items than the expected one");
		} else {
			return new AssertionFailedError("Expected sequence contains more items than the real one");
		}
	}
	
}
