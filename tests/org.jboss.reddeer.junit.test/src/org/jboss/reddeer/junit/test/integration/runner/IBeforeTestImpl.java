package org.jboss.reddeer.junit.test.integration.runner;

import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.test.integration.runner.order.TestSequence;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class IBeforeTestImpl implements IBeforeTest {

	@Override
	public void runBeforeTestClass(String config, TestClass testClass) {
		TestSequence.addIBeforesClass(IBeforeTestImpl.class);
	}
	
	@Override
	public void runBeforeTest(String config, Object target, FrameworkMethod method) {
		TestSequence.addIBefores(IBeforeTestImpl.class);
	}

	@Override
	public boolean hasToRun() {
		return true;
	}
}
