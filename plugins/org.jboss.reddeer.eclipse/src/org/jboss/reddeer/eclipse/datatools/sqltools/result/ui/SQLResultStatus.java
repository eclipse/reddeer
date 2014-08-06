package org.jboss.reddeer.eclipse.datatools.sqltools.result.ui;

public enum SQLResultStatus {

	SUCCEEDED("Succeeded"), FAILED("Failed"), STARTED("Started");

	public String status;

	private SQLResultStatus(String status) {
		this.status = status;
	}

	public static SQLResultStatus fromString(String text) {
		if (text != null) {
			for (SQLResultStatus s : SQLResultStatus.values()) {
				if (text.equalsIgnoreCase(s.status)) {
					return s;
				}
			}
		}
		return null;
	}

}
