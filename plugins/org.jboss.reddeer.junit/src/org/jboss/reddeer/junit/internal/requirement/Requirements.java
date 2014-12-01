package org.jboss.reddeer.junit.internal.requirement;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * Aggregates {@link Requirement} objects and allows to perform tasks on them easily.
 *  
 * @author Lucia Jelinkova
 *
 */
public class Requirements implements Requirement<Annotation>, Iterable<Requirement<?>>{

	private List<Requirement<?>> requirements;
	private Class<?> clazz;
	private String configID;
	private Logger log = Logger.getLogger(Requirements.class);
	
	public Requirements(List<Requirement<?>> requirements, Class<?> clazz, String configID) {
		super();
		if (requirements == null){
			throw new IllegalArgumentException("The requirements list was null");
		}
		if (clazz == null) {
			throw new IllegalArgumentException("The class containing requirements is null");
		}
		this.requirements = requirements;
		this.clazz = clazz;
		this.configID = configID;
	}

	@Override
	public Iterator<Requirement<?>> iterator() {
		return requirements.iterator();
	}
	
	public int size(){
		return requirements.size();
	}
	
	@Override
	public boolean canFulfill() {
		boolean canFulfill = true;
		for (Requirement<?> r : requirements) {
			try {
				boolean canFulfillReq = r.canFulfill();
				log.info("Requirement " + r.getClass() + " can be fulfilled: " + canFulfillReq);
				canFulfill = canFulfill && canFulfillReq;
			} catch (RuntimeException ex) {
				CaptureScreenshot captureScreenshot = new CaptureScreenshot();
				try {
					captureScreenshot.captureScreenshot(configID, 
							CaptureScreenshot.getScreenshotFileName(clazz, null, r.getClass().getSimpleName()));
				} catch (CaptureScreenshotException e) {
					e.printInfo(log);
				}
				throw ex;
			}
		}
		return canFulfill;
	}
	
	@Override
	public void fulfill() {
		for (Requirement<?> r : requirements) {
			try {
				log.info("Fulfilling requirement of " + r.getClass());
				r.fulfill();
			} catch (RuntimeException ex) {
				CaptureScreenshot captureScreenshot = new CaptureScreenshot();
				try {
					captureScreenshot.captureScreenshot(configID, 
							CaptureScreenshot.getScreenshotFileName(clazz, null, r.getClass().getSimpleName()));
				} catch (CaptureScreenshotException e) {
					e.printInfo(log);
				}
				throw ex;
			}
		}
	}
	
	@Override
	public void setDeclaration(Annotation declaration) {
		throw new IllegalStateException("This method should never be called on wrapper object");
	}
}
