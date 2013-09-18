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

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public void setDriverLibrary(String driverLibrary) {
		this.driverLibrary = driverLibrary;
	}

	public void setDriverTemplate(DriverTemplate driverTemplate) {
		this.driverTemplate = driverTemplate;
	}

	public String getDriverLibrary() {
		return driverLibrary;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public DriverTemplate getDriverTemplate() {
		return driverTemplate;
	}

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
