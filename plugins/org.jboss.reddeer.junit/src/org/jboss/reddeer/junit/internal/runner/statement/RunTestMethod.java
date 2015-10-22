package org.jboss.reddeer.junit.internal.runner.statement;

import org.jboss.reddeer.common.logging.Logger;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * Runs the test method and screates screenshot upon unexpected failure. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RunTestMethod extends AbstractStatementWithScreenshot {
	
	private static final Logger log = Logger.getLogger(RunTestMethod.class);

    public RunTestMethod(String config, TestClass testClass, FrameworkMethod testMethod, Object target) {
    	super(config, null, testClass, testMethod, target);
    }

    @Override
    public void evaluate() throws Throwable {
    	try{
    		frameworkMethod.invokeExplosively(target);	
    	} catch (Throwable t){
    		Test annotation = (Test) frameworkMethod.getAnnotations()[0];
    		if (annotation.expected().getName().equals("org.junit.Test$None") ||
    			!annotation.expected().isAssignableFrom(t.getClass())) {
    				log.error("Test " + target.getClass().getName() 
    					+ "." + frameworkMethod.getName()
    					+ " throws exception: ",t);
	    			createScreenshot();
    		}
    		throw t;
    	}
    }
}