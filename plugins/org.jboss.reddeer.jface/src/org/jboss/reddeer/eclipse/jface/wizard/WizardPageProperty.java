package org.jboss.reddeer.eclipse.jface.wizard;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Matches a wizard dialog with a given property
 * 
 * @author apodhrad
 * @since 0.5
 * @deprecated replaced by {@link org.jboss.reddeer.jface.wizard.WizardPageProperty}
 */
public class WizardPageProperty extends BaseMatcher<WizardDialog> {

	private Object key;
	private Object value;

	public WizardPageProperty(Object key, Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean matches(Object item) {
		if (item instanceof WizardDialog) {
			WizardDialog wizardDialog = (WizardDialog) item;
			Object value = wizardDialog.getProperty(this.key);
			if (value != null) {
				return value.equals(this.value);
			} else {
				return this.value == null;
			}
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("with property '" + key + "=" + value + "'");
	}
}
