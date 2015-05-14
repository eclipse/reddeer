package org.jboss.reddeer.snippet.suite;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Customized test suite.
 * Allows to modify test suite initialization and perform pre actions and/or post actions.
 */
public class MyTestSuite extends RedDeerSuite {
	
    /**
     * Test suite constructor.
     * Could be used for some pre and post construct actions.
     */
    public MyTestSuite(Class<?> clazz, RunnerBuilder builder)
            throws InitializationError {
        super(clazz, builder);
    }

    @Override
    public void run(RunNotifier notifier) {
        // put here some actions called before tests run 
        // e.g. close welcome screen        
        super.run(notifier);
        // put here some actions called after tests run
        // e.g. cleanup workspace
    }
}