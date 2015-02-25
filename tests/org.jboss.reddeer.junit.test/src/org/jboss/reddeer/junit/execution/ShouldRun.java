package org.jboss.reddeer.junit.execution;

import org.junit.runners.model.FrameworkMethod;

public class ShouldRun implements TestMethodShouldRun {

	@Override
	public boolean shouldRun(FrameworkMethod method) {
		return true;
	}

}
