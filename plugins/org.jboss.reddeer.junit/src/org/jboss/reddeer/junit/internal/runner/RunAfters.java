package org.jboss.reddeer.junit.internal.runner;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshotException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public class RunAfters extends Statement {
    private final Statement fNext;

    private final Object fTarget;

    private final List<FrameworkMethod> fAfters;
    
    private static final Logger log = Logger.getLogger(RequirementsRunner.class);
    
    private List<IAfterTest> afterTests;

    public RunAfters(Statement next, List<FrameworkMethod> afters, Object target) {
        this(next, afters, target, null);
    }
    
    public RunAfters(Statement next, List<FrameworkMethod> afters, Object target, List<IAfterTest> afterTests) {
        fNext = next;
        fAfters = afters;
        fTarget = target;
        this.afterTests = new ArrayList<IAfterTest>();
        if(afterTests != null) {
        	this.afterTests = afterTests;
        }
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
        				String canonicalName = "";
        				if (fTarget != null) {
        					canonicalName = fTarget.getClass().getCanonicalName();
        				}
                		capturer.captureScreenshot(canonicalName + "-" + each.getName()); 
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
                	capturer.captureScreenshot(afterTest.getClass().getCanonicalName() + "-runAfterTest");
                    errors.add(e);
                }
            }
        }
        MultipleFailureException.assertEmpty(errors);
    }
}
