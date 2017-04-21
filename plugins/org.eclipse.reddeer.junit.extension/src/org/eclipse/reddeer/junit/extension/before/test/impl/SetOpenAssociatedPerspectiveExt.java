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
package org.eclipse.reddeer.junit.extension.before.test.impl;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.direct.preferences.PreferencesUtil;
import org.eclipse.reddeer.junit.extension.ExtensionPriority;
import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * Extension for Extension point org.eclipse.reddeer.junit.before.test. Disables
 * opening to associated perspective when project is created also dialog asking
 * to open to associated perspective is never displayed Use this system property
 * to set open associated perspective behavior:
 *
 * - rd.openAssociatedPerspective=[prompt|always|never]
 * (default=never)
 * 
 * @author vlado pakan
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class SetOpenAssociatedPerspectiveExt implements IBeforeTest {

	private static final Logger log = Logger.getLogger(SetOpenAssociatedPerspectiveExt.class);

	
	@Override
	public void runBeforeTestClass(String config, TestClass testClass) {
		setOpenAssociatedPerspective();		
	}
	
	/** 
	 * See {@link IBeforeTest}.
	 */
	@Override
	public void runBeforeTest(String config, Object target, FrameworkMethod method) {
		// do not run before each test
	}

	/** 
	 * Sets Open Associated Perspective when project is created.
	 */
	private void setOpenAssociatedPerspective() {
		String openAssociatedPerspective = RedDeerProperties.OPEN_ASSOCIATED_PERSPECTIVE.getValue();
		log.debug("Setting open associated perspective to: '" + openAssociatedPerspective + "'");
		PreferencesUtil.setOpenAssociatedPerspective(openAssociatedPerspective);
	}

	/** 
	 * See {@link IBeforeTest}.
	 * @return boolean
	 */
	@Override
	public boolean hasToRun() {
		return true;
	}

	@Override
	public long getPriority() {
		return ExtensionPriority.SET_OPEN_ASSOCIATED_PERSPECTIVE_PRIORITY;
	}

}
