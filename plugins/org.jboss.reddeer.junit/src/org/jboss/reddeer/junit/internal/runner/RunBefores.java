package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.common.logging.Logger;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RunBefores extends Statement {
	private final Statement fNext;

	private final Object fTarget;

	private final List<FrameworkMethod> fBefores;

	private static final Logger log = Logger.getLogger(RequirementsRunner.class);
	
	public RunBefores(Statement next, List<FrameworkMethod> befores,
			Object target) {
		fNext = next;
		fBefores = befores;
		fTarget = target;
	}

	@Override
	public void evaluate() throws Throwable {
		FrameworkMethod before = null;
		try {
			for (FrameworkMethod bfr : fBefores) {
				before = bfr;
				before.invokeExplosively(fTarget);
			}
		} catch (Throwable throwable) {
			CaptureScreenshot capturer = new CaptureScreenshot();
			try {
				String canonicalName = "";
				if (fTarget != null) {
					canonicalName = fTarget.getClass().getCanonicalName();
				}
				capturer.captureScreenshot(canonicalName + "-" + before.getName());
			} catch (CaptureScreenshotException ex) {
				ex.printInfo(log);
			}
			throw throwable;
		}
		fNext.evaluate();
	}
}