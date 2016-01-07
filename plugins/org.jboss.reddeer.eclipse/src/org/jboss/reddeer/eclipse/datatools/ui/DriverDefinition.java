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
package org.jboss.reddeer.eclipse.datatools.ui;


/**
 * Driver Definition.
 * 
 * @author apodhrad
 * 
 */
public class DriverDefinition {

	private String driverName;
	private String driverLibrary;
	private String driverClass;
	private DriverTemplate driverTemplate;

	/**
	 * Gets the driver name.
	 *
	 * @return the driver name
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * Sets the driver name.
	 *
	 * @param driverName the new driver name
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * Sets the driver library.
	 *
	 * @param driverLibrary the new driver library
	 */
	public void setDriverLibrary(String driverLibrary) {
		this.driverLibrary = driverLibrary;
	}

	/**
	 * Sets the driver template.
	 *
	 * @param driverTemplate the new driver template
	 */
	public void setDriverTemplate(DriverTemplate driverTemplate) {
		this.driverTemplate = driverTemplate;
	}

	/**
	 * Gets the driver library.
	 *
	 * @return the driver library
	 */
	public String getDriverLibrary() {
		return driverLibrary;
	}

	/**
	 * Gets the driver class.
	 *
	 * @return the driver class
	 */
	public String getDriverClass() {
		return driverClass;
	}

	/**
	 * Sets the driver class.
	 *
	 * @param driverClass the new driver class
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	/**
	 * Gets the driver template.
	 *
	 * @return the driver template
	 */
	public DriverTemplate getDriverTemplate() {
		return driverTemplate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driverClass == null) ? 0 : driverClass.hashCode());
		result = prime * result + ((driverLibrary == null) ? 0 : driverLibrary.hashCode());
		result = prime * result + ((driverName == null) ? 0 : driverName.hashCode());
		result = prime * result + ((driverTemplate == null) ? 0 : driverTemplate.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverDefinition other = (DriverDefinition) obj;
		if (driverClass == null) {
			if (other.driverClass != null)
				return false;
		} else if (!driverClass.equals(other.driverClass))
			return false;
		if (driverLibrary == null) {
			if (other.driverLibrary != null)
				return false;
		} else if (!driverLibrary.equals(other.driverLibrary))
			return false;
		if (driverName == null) {
			if (other.driverName != null)
				return false;
		} else if (!driverName.equals(other.driverName))
			return false;
		if (driverTemplate == null) {
			if (other.driverTemplate != null)
				return false;
		} else if (!driverTemplate.equals(other.driverTemplate))
			return false;
		return true;
	}

}
