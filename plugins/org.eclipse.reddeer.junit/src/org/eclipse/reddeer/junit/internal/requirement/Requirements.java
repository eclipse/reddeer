/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.internal.requirement;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.execution.PriorityComparator;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.screenshot.CaptureScreenshotException;
import org.eclipse.reddeer.junit.screenshot.ScreenshotCapturer;

/**
 * Aggregates {@link Requirement} objects and allows to perform tasks on them easily.
 *  
 * @author Lucia Jelinkova
 * @author Andrej Podhradsky (apodhrad@redhat.com)
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

	@Override
	public void fulfill() {
		runSafely(Requirement::fulfill);
	}

	@Override
	public void cleanUp() {
		runSafely(Requirement::cleanUp);
	}

	@Override
	public void runBefore() {
		runSafely(Requirement::runBefore);
	}

	@Override
	public void runBeforeClass() {
		runSafely(Requirement::runBeforeClass);
	}

	@Override
	public void runAfter() {
		runSafely(Requirement::runAfter);
	}

	@Override
	public void runAfterClass() {
		runSafely(Requirement::runAfterClass);
	}

	private void runSafely(Consumer<Requirement<?>> run) {
		requirements.forEach(requirement -> runSafely(run, requirement));
	}

	private void runSafely(Consumer<Requirement<?>> run, Requirement<?> requirement) {
		try {
			run.accept(requirement);
		} catch (Throwable t) {
			handleException(t, requirement);
			throw t;
		}
	}

	@Override
	public void setDeclaration(Annotation declaration) {
		throw new IllegalStateException("This method should never be called on wrapper object");
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

	@Override
	public Annotation getDeclaration() {
		throw new IllegalStateException("This method should never be called on wrapper object");
	}
}
