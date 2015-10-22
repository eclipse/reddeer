package org.jboss.reddeer.junit.test.integration.runner;

import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.test.integration.runner.order.TestSequence;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class IAfterTestImpl implements IAfterTest {

	@Override
	public void runAfterTestClass(String config, TestClass testClass) {
		TestSequence.addIAftersClass(IAfterTestImpl.class);
	}
	
	@Override
	public void runAfterTest(String config, Object target, FrameworkMethod method) {
		TestSequence.addIAfters(IAfterTestImpl.class);
	}

	@Override
	public boolean hasToRun() {
		return true;
	}
}
