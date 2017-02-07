/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.internal.requirement;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.execution.PriorityComparator;
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
		requirements.sort(new PriorityComparator());
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
			} catch (Throwable ex) {
				handleException(ex, r);
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
			} catch (Throwable ex) {
				handleException(ex, r);
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
			} catch (Throwable ex) {
				handleException(ex, r);
				throw ex;
			}
		}
		
	}
	
	private void handleException(Throwable ex, Requirement<?> r) {
		if (ScreenshotCapturer.shouldCaptureScreenshotOnException(ex)) {
			ScreenshotCapturer screenshotCapturer = ScreenshotCapturer.getInstance();
			try {
				screenshotCapturer.captureScreenshotOnFailure(configID,
						ScreenshotCapturer.getScreenshotFileName(clazz, null, r.getClass().getSimpleName()));
			} catch (CaptureScreenshotException e) {
				e.printInfo(log);
			}
		}
	}
}
