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
package org.eclipse.reddeer.requirements.cleanerrorlog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.ui.views.log.LogView;
import org.eclipse.reddeer.junit.requirement.AbstractRequirement;
import org.eclipse.reddeer.requirements.cleanerrorlog.CleanErrorLogRequirement.CleanErrorLog;

/**
 * This requirement ensures that all messages in Error Log will be deleted.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 * 
 */
public class CleanErrorLogRequirement extends AbstractRequirement<CleanErrorLog> {

	private static final Logger log = Logger.getLogger(CleanErrorLogRequirement.class);

	/**
	 * Marks test class, which requires cleaning Error Log before test cases are
	 * executed.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Documented
	public @interface CleanErrorLog {

	}

	/**
	 * Deletes all messages in Error Log.
	 */
	@Override
	public void fulfill() {
		log.info("Clean all records in Error Log");
		new LogView().open();
		new LogView().deleteLog();
	}

	@Override
	public void cleanUp() {

	}

}
