/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.watcher;

import java.io.IOException;

import org.eclipse.reddeer.common.context.ExecutionSetting;
import org.eclipse.reddeer.common.logging.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * RedDeer Watchdog hooks executed tests and performs operations when if test
 * failes
 * 
 * @author Jiri Peterka
 * 
 */
public class RedDeerWatchdog extends TestWatcher {
	Logger log = Logger.getLogger(RedDeerWatchdog.class);

	/* (non-Javadoc)
	 * @see org.junit.rules.TestWatcher#failed(java.lang.Throwable, org.junit.runner.Description)
	 */
	@Override
	protected void failed(Throwable e, Description description) {
		if (ExecutionSetting.getInstance().isPauseFailedTest()) {
			try {
				log.info("Test execution is paused because of failing test, press [Enter] to continue");
				System.in.read();
			} catch (IOException ex) {
				log.error(ex.getMessage());
			}
		}

	}
}