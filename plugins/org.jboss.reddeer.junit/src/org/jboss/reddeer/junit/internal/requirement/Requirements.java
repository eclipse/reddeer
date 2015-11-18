package org.jboss.reddeer.junit.internal.requirement;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.junit.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.junit.screenshot.ScreenshotCapturer;

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
	
	/**
	 * Instantiates a new requirements.
	 *
	 * @param requirements the requirements
	 * @param clazz the clazz
	 * @param configID the config id
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Requirement<?>> iterator() {
		return requirements.iterator();
	}
	
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size(){
		return requirements.size();
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.Requirement#canFulfill()
	 */
	@Override
	public boolean canFulfill() {
		boolean canFulfill = true;
		for (Requirement<?> r : requirements) {
			try {
				boolean canFulfillReq = r.canFulfill();
				log.info("Requirement " + r.getClass() + " can be fulfilled: " + canFulfillReq);
				canFulfill = canFulfill && canFulfillReq;
			} catch (RuntimeException ex) {
				ScreenshotCapturer screenshotCapturer = ScreenshotCapturer.getInstance();
				try {
					screenshotCapturer.captureScreenshotOnFailure(configID, 
							ScreenshotCapturer.getScreenshotFileName(clazz, null, r.getClass().getSimpleName()));
				} catch (CaptureScreenshotException e) {
					e.printInfo(log);
				}
				throw ex;
			}
		}
		return canFulfill;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.Requirement#fulfill()
	 */
	@Override
	public void fulfill() {
		for (Requirement<?> r : requirements) {
			try {
				log.info("Fulfilling requirement of " + r.getClass());
				r.fulfill();
			} catch (RuntimeException ex) {
				ScreenshotCapturer screenshotCapturer = ScreenshotCapturer.getInstance();
				try {
					screenshotCapturer.captureScreenshotOnFailure(configID, 
							ScreenshotCapturer.getScreenshotFileName(clazz, null, r.getClass().getSimpleName()));
				} catch (CaptureScreenshotException e) {
					e.printInfo(log);
				}
				throw ex;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.Requirement#setDeclaration(java.lang.annotation.Annotation)
	 */
	@Override
	public void setDeclaration(Annotation declaration) {
		throw new IllegalStateException("This method should never be called on wrapper object");
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {
		for (Requirement<?> r : requirements) {
			try {
				log.info("Cleaning up requirement of " + r.getClass());
				r.cleanUp();
			} catch (RuntimeException ex) {
				ScreenshotCapturer screenshotCapturer = ScreenshotCapturer.getInstance();
				try {
					screenshotCapturer.captureScreenshotOnFailure(configID, 
							ScreenshotCapturer.getScreenshotFileName(clazz, null, r.getClass().getSimpleName()));
				} catch (CaptureScreenshotException e) {
					e.printInfo(log);
				}
				throw ex;
			}
		}
		
	}
}
