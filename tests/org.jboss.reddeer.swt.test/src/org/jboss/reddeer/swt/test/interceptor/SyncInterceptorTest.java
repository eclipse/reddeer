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
package org.jboss.reddeer.swt.test.interceptor;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.core.interceptor.ISyncInterceptor;
import org.jboss.reddeer.core.interceptor.SyncInterceptorManager;
import org.jboss.reddeer.core.util.Display;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for basic SyncInterceptor functionality
 * @author Jiri Peterka
 *
 */
@RunWith(RedDeerSuite.class)
public class SyncInterceptorTest {

	protected static final String SHELL_TITLE = "Keyboard testing shell";

	private SyncInterceptorManager sim = SyncInterceptorManager.getInstance();
	private Logger log = Logger.getLogger(SyncInterceptorTest.class);
	private boolean calledBefore = false;
	private boolean calledAfter = false;
	private final String INTERCEPTOR = "simtest";

	class TestInterceptor implements ISyncInterceptor {

		@Override
		public void beforeSyncOp() {
			log.info("Test interceptor before code performed");
			calledBefore = true;

		}

		@Override
		public void afterSyncOp() {
			log.info("Test interceptor after code performed");
			calledAfter = true;

		}
	}

	@Test(expected = RedDeerException.class)
	public void interceptorAlreadyRegistredTest() {
		if (!sim.isRegistered(INTERCEPTOR)) {
			sim.register(INTERCEPTOR, new TestInterceptor());
		}
		sim.register(INTERCEPTOR, new TestInterceptor());
	}

	@Test(expected = RedDeerException.class)
	public void interceptorNotRegisteredTest() {
		if (sim.isRegistered(INTERCEPTOR)) {
			sim.unregister(INTERCEPTOR);
		}
		sim.unregister(INTERCEPTOR);
	}

	@Test
	public void interceptorCalledTest() {
		calledBefore = false;
		calledBefore = false;
		if (!sim.isRegistered(INTERCEPTOR)) {
			sim.register(INTERCEPTOR, new TestInterceptor());
		}

		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				log.info("Code performed inside syncExec()");
			}
		});

		assertTrue("Interceptor before() call is expected", calledBefore);
		assertTrue("Interceptor after() call is expected", calledAfter);
	}
	
	@After
	public void cleanUp() {
		if (sim.isRegistered(INTERCEPTOR)) {
			sim.unregister(INTERCEPTOR);
		}
	}
}
