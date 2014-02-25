package org.jboss.reddeer.junit.internal.runner;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public class RunAfters extends Statement {
    private final Statement fNext;

    private final Object fTarget;

    private final List<FrameworkMethod> fAfters;

    public RunAfters(Statement next, List<FrameworkMethod> afters, Object target) {
        fNext = next;
        fAfters = afters;
        fTarget = target;
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
                	capturer.captureScreenshot(fTarget.getClass().getCanonicalName() + "-" + each.getName());
                    errors.add(e);
                }
            }
        }
        MultipleFailureException.assertEmpty(errors);
    }
}
