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
package org.jboss.reddeer.junit.internal.screenrecorder;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * If recordScreenCast property is set to true, this recorder records every
 * tests, but paased test videos are immediately deleted to preserve disk space.
 * Failed tests videos are left untouched. Videos are stored under screencasts/*.mov
 * 
 */

public class ScreenCastingRunListener extends RunListener {

	private static final Logger log = Logger.getLogger(ScreenCastingRunListener.class);

	private File outputVideoFile = null;
	private boolean wasFailure = false;

	private static boolean SAVE_SCREENCAST = RedDeerProperties.RECORD_SCREENCAST.getBooleanValue();
	private static ScreenRecorderExt screenRecorderExt = null;

	/* (non-Javadoc)
	 * @see org.junit.runner.notification.RunListener#testFailure(org.junit.runner.notification.Failure)
	 */
	@Override
	public void testFailure(Failure failure) throws Exception {
		wasFailure = true;
		super.testFailure(failure);
	}

	/* (non-Javadoc)
	 * @see org.junit.runner.notification.RunListener#testFinished(org.junit.runner.Description)
	 */
	@Override
	public void testFinished(Description description) throws Exception {
		if (SAVE_SCREENCAST) {
			stopScreenRecorder();
			if (!wasFailure) {
				log.info("Deleting test screencast file: " + outputVideoFile.getAbsolutePath());
				outputVideoFile.delete();
			}
		}
		super.testFinished(description);
	}

	/* (non-Javadoc)
	 * @see org.junit.runner.notification.RunListener#testStarted(org.junit.runner.Description)
	 */
	@Override
	public void testStarted(Description description) throws Exception {
		wasFailure = false;
		if (SAVE_SCREENCAST) {
			outputVideoFile = startScreenRecorder(description.toString());
		}
		super.testStarted(description);
	}

	/**
	 * Starts Screen Recorder
	 */
	private static File startScreenRecorder(String className) {
		File outputVideoFile = null;
		if (screenRecorderExt == null) {
			try {
				screenRecorderExt = new ScreenRecorderExt();
			} catch (IOException ioe) {
				log.error("Unable to initialize Screen Recorder.", ioe);
			} catch (AWTException awte) {
				log.error("Unable to initialize Screen Recorder.", awte);
			}
		}
		if (screenRecorderExt != null) {
			if (!screenRecorderExt.isState(ScreenRecorderExt.STATE_DONE)) {
				// try to reset Screen Recorder
				stopScreenRecorder();
			}
			if (screenRecorderExt.isState(ScreenRecorderExt.STATE_DONE)) {
				try {
					File screenCastDir = new File("screencasts");
					if (!screenCastDir.exists()) {
						screenCastDir.mkdir();
					}
					final String fileName = "screencasts" + File.separator + className + ".mov";
					log.info("Starting Screen Recorder. Saving Screen Cast to file: " + fileName);
					screenRecorderExt.start(fileName);
					outputVideoFile = new File(fileName);
				} catch (IOException ioe) {
					log.error("Unable to start Screen Recorder.", ioe);
				}
			} else {
				log.error("Unable to start Screen Recorder.\nScreen Recorder is not in state DONE.\nCurrent status: "
						+ screenRecorderExt.getPublicState());
			}
		} else {
			log.error("Screen Recorder was not properly initilized");
		}
		return outputVideoFile;
	}

	/**
	 * Stops Screen Recorder
	 */
	private static void stopScreenRecorder() {
		if (screenRecorderExt != null) {
			try {
				if (!screenRecorderExt.isState(ScreenRecorderExt.STATE_RECORDING)) {
					log.error("Stopping Screen Recorder.\nScreen Recorder is not in state RECORDING.\nCurrent status: "
							+ screenRecorderExt.getPublicState());
				}
				screenRecorderExt.stop();
				log.info("Screen Recorder stopped.");
			} catch (IOException ioe) {
				log.error("Unable to stop Screen Recorder.", ioe);
			}
		} else {
			log.error("Unable to stop Screen Recorder.\nScreen Recorder was not properly initilized");
		}
	}
}
