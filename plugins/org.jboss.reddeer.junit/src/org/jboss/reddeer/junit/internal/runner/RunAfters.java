package org.jboss.reddeer.junit.internal.runner;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.common.logging.Logger;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Statement which run after tests or classes. Upon failure a screenshot is captured.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RunAfters extends Statement {
    private final Statement fNext;

    private final Object fTarget;

    private final List<FrameworkMethod> fAfters;
    
    private final FrameworkMethod fMethod;
    
    private TestClass testClass;
    
    private static final Logger log = Logger.getLogger(RequirementsRunner.class);
    
    private String config;
    
    private List<IAfterTest> afterTests;

    public RunAfters(String config, FrameworkMethod method, Statement next, List<FrameworkMethod> afters, 
    		Object target, TestClass testClass) {
        this(config, method, next, afters, target, null, testClass);
    }
    
    public RunAfters(String config, FrameworkMethod method, Statement next, List<FrameworkMethod> afters, 
    		Object target, List<IAfterTest> afterTests, TestClass testClass) {
        fNext = next;
        fAfters = afters;
        fTarget = target;
        fMethod = method;
        this.afterTests = new ArrayList<IAfterTest>();
        if(afterTests != null) {
        	this.afterTests = afterTests;
        }
        if (testClass != null) {
        	this.testClass = testClass;
        }
        this.config = config;
    }

    @Override
    public void evaluate() throws Throwable {
        List<Throwable> errors = new ArrayList<Throwable>();
        try {
            fNext.evaluate();
        } catch (Throwable e) {
            errors.add(e);
        } finally {
            for (FrameworkMethod each : fAfters) {
                try {
                    each.invokeExplosively(fTarget);
                } catch (Throwable e) {
                	CaptureScreenshot capturer = new CaptureScreenshot();
                	try {
                		String methodName = "";
                		if (fMethod != null) {
                			methodName = "#" + fMethod.getName();
                		}
                		String fileName;
                		if (fTarget == null) {
                			fileName = testClass.getJavaClass().getSimpleName()
                					+ "@AfterClass#" + each.getName() + "["
                					+ testClass.getJavaClass().getPackage().getName() + "]";
                		} else {
                			fileName = fTarget.getClass().getSimpleName() 
                				+ methodName + "@After#" + each.getName()
        						+ "[" + fTarget.getClass().getPackage().getName() + "]";
                		}
        				capturer.captureScreenshot(config, fileName);
                	} catch (CaptureScreenshotException ex) {
                		ex.printInfo(log);
                	}
                    errors.add(e);
                }
            }
            for (IAfterTest afterTest : afterTests) {
                try {
                	afterTest.runAfterTest(fTarget);
                } catch (Throwable e) {
                	CaptureScreenshot capturer = new CaptureScreenshot();
                	try {
                		String methodName = "";
                		if (fMethod != null) {
                			methodName = "#" + fMethod.getName();
                		}
                		String fileName;
                		if (fTarget == null) {
                			fileName = testClass.getJavaClass().getSimpleName()
                					+ "@IAfter#" + afterTest.getClass().getCanonicalName()
                					+ "[" + testClass.getJavaClass().getPackage().getName() + "]";
                		} else {
                			fileName = fTarget.getClass().getSimpleName() 
                					+ methodName + "@IAfter#" 
                					+ afterTest.getClass().getCanonicalName()
                					+ "[" + fTarget.getClass().getPackage().getName() + "]";
                		}
        				capturer.captureScreenshot(config, fileName);
                	} catch (CaptureScreenshotException ex) {
                		ex.printInfo(log);
                	}
                    errors.add(e);
                }
            }
        }
        MultipleFailureException.assertEmpty(errors);
    }
}
