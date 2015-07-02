package org.jboss.reddeer.junit.test.execution;

import org.jboss.reddeer.junit.execution.TestMethodShouldRun;
import org.junit.runners.model.FrameworkMethod;

public class ShouldNotRun implements TestMethodShouldRun {

	@Override
	public boolean shouldRun(FrameworkMethod method) {
		return false;
	}

	
}
