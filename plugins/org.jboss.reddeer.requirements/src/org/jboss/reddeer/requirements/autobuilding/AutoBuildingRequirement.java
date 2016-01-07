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
package org.jboss.reddeer.requirements.autobuilding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.direct.preferences.PreferencesUtil;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.requirements.autobuilding.AutoBuildingRequirement.AutoBuilding;

/**
 * This requirement ensures that the setting for auto building is set on/off. During the cleanup phase the change is set
 * back.
 * 
 * @author Andrej Podhradsky
 *
 */
public class AutoBuildingRequirement implements Requirement<AutoBuilding> {

	private AutoBuilding autoBuilding;
	private boolean autoBuildingOriginalValue;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface AutoBuilding {

		/**
		 * Value.
		 *
		 * @return true if the auto building is on, false otherwise
		 */
		boolean value() default true;

		/**
		 * Cleanup. The default value is true.
		 *
		 * @return true if the cleanup is required, false otherwise
		 */
		boolean cleanup() default true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.reddeer.junit.requirement.Requirement#canFulfill()
	 */
	@Override
	public boolean canFulfill() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.reddeer.junit.requirement.Requirement#fulfill()
	 */
	@Override
	public void fulfill() {
		autoBuildingOriginalValue = PreferencesUtil.isAutoBuildingOn();
		if (autoBuilding.value()) {
			PreferencesUtil.setAutoBuildingOn();
		} else {
			PreferencesUtil.setAutoBuildingOff();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.reddeer.junit.requirement.Requirement#setDeclaration(java.lang. annotation.Annotation)
	 */
	@Override
	public void setDeclaration(AutoBuilding autoBuilding) {
		this.autoBuilding = autoBuilding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {
		if (!autoBuilding.cleanup()) {
			return;
		}
		if (autoBuildingOriginalValue) {
			PreferencesUtil.setAutoBuildingOn();
		} else {
			PreferencesUtil.setAutoBuildingOff();
		}
	}
}
