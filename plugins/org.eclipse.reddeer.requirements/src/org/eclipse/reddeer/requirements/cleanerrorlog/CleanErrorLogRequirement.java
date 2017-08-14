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
package org.eclipse.reddeer.requirements.cleanerrorlog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.ui.views.log.LogView;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.requirements.cleanerrorlog.CleanErrorLogRequirement.CleanErrorLog;

/**
 * This requirement ensures that all messages in Error Log will be deleted.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 * 
 */
public class CleanErrorLogRequirement implements Requirement<CleanErrorLog> {

	private static final Logger log = Logger.getLogger(CleanErrorLogRequirement.class);

	private CleanErrorLog cleanErrorLog;

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
	public void setDeclaration(CleanErrorLog cleanErrorLog) {
		this.cleanErrorLog = cleanErrorLog;
	}

	@Override
	public CleanErrorLog getDeclaration() {
		return cleanErrorLog;
	}

	@Override
	public void cleanUp() {

	}

}
