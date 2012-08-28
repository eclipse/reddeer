package org.jboss.reddeer.wiki.howto.suite;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class MyTestSuite extends RedDeerSuite {

	public MyTestSuite(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(clazz, builder);
	}

	@Override
	public void run(RunNotifier notifier) {
		// close usage dialog
		
		super.run(notifier);
		
		// cleanup workspace
	}
}
